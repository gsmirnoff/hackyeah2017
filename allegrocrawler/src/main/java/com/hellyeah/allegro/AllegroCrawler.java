package com.hellyeah.allegro;

import com.hellyeah.http.HttpClient;
import com.hellyeah.http.HttpClientException;

import java.util.*;
import java.util.stream.Collectors;

public class AllegroCrawler {

	private static final String BASE_URL = "https://allegro.pl/kategoria/elektronika";
	private static final String PAGE_PARAM = "p";
	private Map<String, Object> searchParameters;
	private HttpClient httpClient;

	// https://allegro.pl/kategoria/elektronika?string=vat%20marza&stan=nowe&order=m&bmatch=ss-base-relevance-floki-5-nga-hcp-ele-1-4-1003&p=2

	public static class AllegroAuction {

		private String url;
		private String nickname;
		private String nIP;
		private String email;
		private List<String> phones = new ArrayList<>();

		private Map<String, String> parameters;

		public String getNickname() {
			return nickname;
		}

		public String getnIP() {
			return nIP;
		}

		public String getEmail() {
			return email;
		}

		public List<String> getPhones() {
			return phones;
		}

		public AllegroAuction withNickname(String nickname) {
			this.nickname = nickname;
			return this;
		}

		public AllegroAuction withnIP(String nIP) {
			this.nIP = nIP;
			return this;
		}

		public AllegroAuction withEmail(String email) {
			this.email = email;
			return this;
		}

		public AllegroAuction withPhone(String phone) {
			phones.add(phone);
			return this;
		}
	}

	public List<AllegroAuction> fetchAllegroAuctions(int auctionsCount) {
		List<AllegroAuction> auctions = new ArrayList<>(auctionsCount);

		String searchResultsPage = search();

		while (auctions.size() <= auctionsCount) {
			auctions.addAll(retrieveAuctions(searchResultsPage));

			searchResultsPage = nextSearchPage();
		}

		return auctions;
	}

	private String search() {
//		return httpClient.get();
		return null;
	}

	private String nextSearchPage() {
		return null;
	}

	private List<AllegroAuction> retrieveAuctions(String searchPage) {
		return getAuctionUrls(searchPage).stream()
					.map(this::loadAuctionPage)
					.filter(Objects::nonNull)
					.map(this::parseAuctionPage)
					.collect(Collectors.toList());
	}

	private List<String> getAuctionUrls(String searchPage) {
		return null;
	}

	private String loadAuctionPage(String url) {
		try {
			return httpClient.get(url, Collections.emptyMap());
		} catch (HttpClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	private AllegroAuction parseAuctionPage(String auctionPage) {
		return null;
	}

	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public void setSearchParameters(Map<String, Object> searchParameters) {
		this.searchParameters = searchParameters;
	}

}
