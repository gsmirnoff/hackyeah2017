package com.hackaton.crawler;

import com.hackaton.crawler.allegro.AllegroCrawler;
import com.hackaton.crawler.export.CSVFormattedWriter;
import com.hackaton.crawler.export.CSVWriter;
import com.hackaton.crawler.http.HttpClient;
import com.hackaton.crawler.http.UnirestHttpClient;
import com.hackaton.crawler.model.Auction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllegroCrawlerApp {

	private static final List<String> UNEXPECTED_KEYWORDS = Arrays.asList(
			"STAN  technicznie nowy - 100% sprawny i wydajny",
			"niemal nowy",
			"Włączony kilkanaście razy",
			"Pierwotna data zakupu",
			"jak NOWY",
			"powystawowy",
			"Rynek wtórny"
	);

	public static void main(String... args) throws Exception {
		AllegroCrawler crawler	= new AllegroCrawler();

		HttpClient httpClient = new UnirestHttpClient();
		crawler.setHttpClient(httpClient);

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("stan", "nowe");
		parameters.put("order", "m");
		parameters.put("string", "marża");

		crawler.setSearchParameters(parameters);
		crawler.setUnexpectedWords(UNEXPECTED_KEYWORDS);

		CSVFormattedWriter writer = new CSVFormattedWriter();
		writer.setHeader("#nick;nip;email;phone,,");

		CSVWriter csvWriter = new CSVWriter();
		csvWriter.setDirectory("c:\\Development\\projects\\hackyeah\\");
		writer.setWriter(csvWriter);

		final int auctionCount = 10000;
		List<Auction> auctionList = crawler.fetch(auctionCount, 1);
		writer.writeAll(auctionList);
	}

}
