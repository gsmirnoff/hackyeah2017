package com.hackaton.crawler.allegro;

import com.hackaton.crawler.DataFetcher;
import com.hackaton.crawler.DataFetcherException;
import com.hackaton.crawler.http.HttpClient;
import com.hackaton.crawler.http.HttpClientException;
import com.hackaton.crawler.model.Auction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class AllegroCrawler implements DataFetcher {

	private static final Logger LOGGER = LoggerFactory.getLogger(AllegroCrawler.class);

	private static final String BASE_URL = "https://allegro.pl/kategoria/elektronika";
	private static final String PAGE_PARAM = "p";

	private static final Pattern SEARCH_DATA_PATTERN = Pattern.compile("\"name\":\\s*\"[^\"]+\",\\s*\"url\":\\s*\"([^\"]+)\",\\s*\"vendor\":\\s*\"[^\"]+\",\\s*\"location\":\\s*\\{\\s*\"country\":\\s*\"PL\",\\s*\"city\":\\s*\"[^\"]+\"\\s*\\},\\s*\"seller\":\\s*\\{\\s*\"id\":\\s*\"(\\d+)\"", Pattern.DOTALL);
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
	public List<Auction> fetch(int auctionsCount, int searchPage) throws DataFetcherException {
		LOGGER.info("Start fetching " + auctionsCount + " auctions from Allegro");

		List<Auction> auctions = new ArrayList<>(auctionsCount);

		String searchResultsPage;
		try {
			searchResultsPage = search(null);
		} catch (HttpClientException e) {
			throw new DataFetcherException("Unable to fetcher auctions from Allegro", e);
		}

		while (auctions.size() <= auctionsCount) {
			List<Auction> result = retrieveAuctions(searchResultsPage);

			if (result.isEmpty()) {
				LOGGER.info("No auctions found on a page.");
				break;
			}

			auctions.addAll(result);

			try {
				searchResultsPage = nextSearchPage(++ searchPage);
			} catch (HttpClientException e) {
				throw new DataFetcherException("Unable to fetcher next page " + searchPage + " from Allegro", e);
			}
		}

		LOGGER.info("Auctions fetched: " + auctions);

		return removeDuplicates(auctions);
	}

	private String search(String page) throws HttpClientException {
		LOGGER.info("Search by URL " + BASE_URL);

		Map<String, Object> searchParameters = new HashMap<>();
		searchParameters.putAll(this.searchParameters);

		if (page != null)
			searchParameters.put(PAGE_PARAM, page);

		return httpClient.get(BASE_URL, searchParameters);
	}

	private String nextSearchPage(int nextPage) throws HttpClientException {
		return search(nextPage + "");
	}

	private List<Auction> retrieveAuctions(String searchPage) throws DataFetcherException {
		LOGGER.info("Retrieve auctions from search page.");

		return getSearchPageData(searchPage).stream()
					.filter(isExpectedAuction())
					.map(s -> loadPage(prepareSellerUrl(s.sellerId)))
					.map(this::parseSellerPage)
					.collect(Collectors.toList());
	}

	private List<SearchPageData> getSearchPageData(String searchPage) throws DataFetcherException {
		Matcher m = SEARCH_DATA_PATTERN.matcher(searchPage);

		List<SearchPageData> searchDataList = new ArrayList<>();
		while(m.find()) {
			searchDataList.add(new SearchPageData(encodeUrl(m.group(1).trim()), m.group(2).trim()));
		}

		LOGGER.info("SearchData found: " + searchDataList);

		return searchDataList;
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
						LOGGER.info("Found exceptional auction, skipping: " + s);
						return false;
					}
				}
			}

			return true;
		};
	}

	private String loadPage(String url) {
//		try {
//			Thread.sleep(2000L);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}

		LOGGER.info("Load page " + url);

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
		return getByMatcher(sellerPage, NIP_PATTERN);
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

	private List<Auction> removeDuplicates(List<Auction> auctionsWithDuplicates) {
		List<Auction> auctions = new ArrayList<>();

		Set<String> nicknames = new HashSet<>();
		for (Auction auction : auctionsWithDuplicates) {
			if (!nicknames.contains(auction.getNickname())) {
				auctions.add(auction);
			}

			nicknames.add(auction.getNickname());
		}

		return auctions;
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
