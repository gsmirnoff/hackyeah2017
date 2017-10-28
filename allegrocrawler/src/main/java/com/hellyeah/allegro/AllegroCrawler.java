package com.hellyeah.allegro;

import com.hellyeah.DataFetcher;
import com.hellyeah.DataFetcherException;
import com.hellyeah.http.HttpClient;
import com.hellyeah.http.HttpClientException;
import com.hellyeah.model.Auction;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AllegroCrawler implements DataFetcher {

	private static final String BASE_URL = "https://allegro.pl/kategoria/elektronika";
	private static final String PAGE_PARAM = "p";

	private static final Pattern ARTICLE_PATTERN_2 = Pattern.compile("\"name\":\\s*\"[^\"]+\",\\s*\"url\":\\s*\"([^\"]+)\",\\s*\"vendor\":\\s*\"[^\"]+\",\\s*\"location\":\\s*\\{\\s*\"country\":\\s*\"PL\",\\s*\"city\":\\s*\"[^\"]+\"\\s*\\},\\s*\"seller\":\\s*\\{\\s*\"id\":\\s*\"(\\d+)\"", Pattern.DOTALL);
	private static final Pattern ARTICLE_DESCRIPTION_PATTERN = Pattern.compile("<section class=\"description-block\">(.+)<div class=\"panel-group\" id=\"accordion\">", Pattern.DOTALL);

	private static final Pattern NICK_NAME_PATTERN = Pattern.compile("<span class=\"uname\">([^<]+)</span>");
	private static final Pattern NIP_PATTERN = Pattern.compile("<div class=\".*seller-user-nip.*\">NIP:\\s*([^<]+)</div>");
	private static final Pattern EMAIL_PATTERN = Pattern.compile("href=\"mailto:([^\"]+)\"");
	private static final Pattern PHONE_PATTERN = Pattern.compile("href=\"tel:([^\"]+)\"");


	private List<String> expectedWords = new ArrayList<>();
	private List<String> unexpectedWords = new ArrayList<>();

	private Map<String, Object> searchParameters;
	private HttpClient httpClient;

	@Override
	public List<Auction> fetch(int auctionsCount) throws DataFetcherException {
		List<Auction> auctions = new ArrayList<>(auctionsCount);

		String searchResultsPage;
		try {
			searchResultsPage = search();
		} catch (HttpClientException e) {
			throw new DataFetcherException("Unable to fetcher auctions from Allegro", e);
		}

		while (auctions.size() <= auctionsCount) {
			auctions.addAll(retrieveAuctions(searchResultsPage));

			searchResultsPage = nextSearchPage();
		}

		return auctions;
	}

	private String search() throws HttpClientException {
		return httpClient.get(BASE_URL, searchParameters);
	}

	private String nextSearchPage() {
		return null;
	}

	private List<Auction> retrieveAuctions(String searchPage) throws DataFetcherException {
		return getSearchPageData(searchPage).stream()
					.filter(isExpectedAuction())
					.map(s -> loadPage(prepareSellerUrl(s.sellerId)))
					.map(this::parseSellerPage)
					.collect(Collectors.toList());
	}

	private List<SearchPageData> getSearchPageData(String searchPage) throws DataFetcherException {
		Matcher m = ARTICLE_PATTERN_2.matcher(searchPage);

		List<SearchPageData> articles = new ArrayList<>();
		while(m.find()) {
			articles.add(new SearchPageData(encodeUrl(m.group(1).trim()), m.group(2).trim()));
		}

		return articles;
	}

	private String encodeUrl(String url) throws DataFetcherException {
		return url.replaceAll("\\\\u002F", "/");
//		try {
//			return new String(url.getBytes("UTF-8"), "UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			throw new DataFetcherException(e);
//		}
	}

	private Predicate<SearchPageData> isExpectedAuction() {
		return s ->  {
			String auctionPage = loadPage(s.url);
			String auctionDescription = getAuctionDescription(auctionPage);

			if (!unexpectedWords.isEmpty()) {
				for (String expectedWord : unexpectedWords) {
					if (auctionDescription.contains(expectedWord)) {
						return false;
					}
				}
			}

			return true;
		};
	}

	private String loadPage(String url) {
		try {
			return httpClient.get(url, Collections.emptyMap());
		} catch (HttpClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	private String getAuctionDescription(String auctionPage) {
		Matcher m = ARTICLE_DESCRIPTION_PATTERN.matcher(auctionPage);

		if (m.find()) {
			return m.group(1);
		}

		return "";
	}

	private String prepareSellerUrl(String sellerId) {
		return "http://allegro.pl/sellerInfoFrontend/" + sellerId + "/aboutSeller";
	}

	private Auction parseSellerPage(String sellerPage) {
		return new Auction()
					.withNickname(getNickname(sellerPage))
					.withnIP(getNIP(sellerPage))
					.withEmail(getEmail(sellerPage))
					.withPhones(getPhones(sellerPage));
	}

	private String getNickname(String sellerPage) {
		String nickName = getByMatcher(sellerPage, NICK_NAME_PATTERN);
		if (nickName.contains("Client:")) {
			nickName = nickName.replace("Client:", "");
		}
		return nickName;
	}

	private String getNIP(String sellerPage) {
		return getByMatcher(sellerPage, NICK_NAME_PATTERN);
	}

	private String getEmail(String sellerPage) {
		return getByMatcher(sellerPage, EMAIL_PATTERN);
	}

	private List<String> getPhones(String sellerPage) {
		Matcher m = PHONE_PATTERN.matcher(sellerPage);

		List<String> phones = new ArrayList<>();
		while (m.find()) {
			phones.add(m.group(1));
		}

		return phones;
	}

	private String getByMatcher(String string, Pattern p) {
		Matcher m = p.matcher(string);
		return m.find() ? m.group(1) : "";
	}

	private static class SearchPageData {
		private String url;
		private String sellerId;

		public SearchPageData(String url, String sellerId) {
			this.url = url;
			this.sellerId = sellerId;
		}
	}

	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public void setSearchParameters(Map<String, Object> searchParameters) {
		this.searchParameters = searchParameters;
	}

	public void setExpectedWords(List<String> expectedWords) {
		this.expectedWords = expectedWords;
	}

	public void setUnexpectedWords(List<String> unexpectedWords) {
		this.unexpectedWords = unexpectedWords;
	}
}
