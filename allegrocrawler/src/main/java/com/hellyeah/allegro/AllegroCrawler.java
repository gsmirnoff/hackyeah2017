package com.hellyeah.allegro;

import com.hellyeah.DataFetcher;
import com.hellyeah.DataFetcherException;
import com.hellyeah.http.HttpClient;
import com.hellyeah.http.HttpClientException;
import com.hellyeah.model.Auction;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AllegroCrawler implements DataFetcher {

	private static final String BASE_URL = "https://allegro.pl/kategoria/elektronika";
	private static final String PAGE_PARAM = "p";
	private Map<String, Object> searchParameters;
	private HttpClient httpClient;

	// https://allegro.pl/kategoria/elektronika?string=vat%20marza&stan=nowe&order=m&bmatch=ss-base-relevance-floki-5-nga-hcp-ele-1-4-1003&p=2

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

	private List<Auction> retrieveAuctions(String searchPage) {
		return getAuctionUrls(searchPage).stream()
					.map(this::loadAuctionPage)
					.filter(Objects::nonNull)
					.map(this::parseAuctionPage)
					.collect(Collectors.toList());
	}

	private List<String> getAuctionUrls(String searchPage) {
		List<String> urls = new ArrayList<>();
		List<String> articles = findAllArticles(searchPage);

		for (String article : articles) {
			urls.addAll(fetchUrlsFromArticle(article));
		}

		return urls;
	}

	private static final Pattern ARTICLE_PATTERN = Pattern.compile("<article.*>(.+)</article>", Pattern.DOTALL);
	private static final Pattern ARTICLE_URL_PATTERN = Pattern.compile("<article.*>(.+)</article>", Pattern.DOTALL);

	private List<String> findAllArticles(String searchPage) {
		Matcher m = ARTICLE_PATTERN.matcher(searchPage);

		List<String> articles = new ArrayList<>();
		while(m.find()) {
			articles.add(m.group(1).trim());
		}
		return articles;
	}

	private List<String> fetchUrlsFromArticle(String article) {
		Matcher m = ARTICLE_URL_PATTERN.matcher(article);

		List<String> urls = new ArrayList<>();
		while (m.find()) {
			urls.add(m.group(1));
		}

		return urls;
	}

	private String loadAuctionPage(String url) {
		try {
			return httpClient.get(url, Collections.emptyMap());
		} catch (HttpClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	private Auction parseAuctionPage(String auctionPage) {
		return null;
	}

	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public void setSearchParameters(Map<String, Object> searchParameters) {
		this.searchParameters = searchParameters;
	}

}
