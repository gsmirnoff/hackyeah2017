package com.hellyeah.allegro;

import com.hellyeah.http.HttpClient;
import com.hellyeah.http.UnirestHttpClient;
import com.hellyeah.model.Auction;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllegroCrawlerTest {

	private AllegroCrawler crawler;

	@Before
	public void setUp() throws Exception {
		crawler	= new AllegroCrawler();

		HttpClient httpClient = new UnirestHttpClient();
		crawler.setHttpClient(httpClient);

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("stan", "nowe");
		parameters.put("order", "m");
		parameters.put("string", "marża");

		crawler.setSearchParameters(parameters);
	}

	@Test
	public void testCrawler() throws Exception {
		// given
		final int auctionsAmount = 30;

		// when
		List<Auction> auctions = crawler.fetch(auctionsAmount, 0);

		// then
		System.out.println(auctions);
		//
	}
}