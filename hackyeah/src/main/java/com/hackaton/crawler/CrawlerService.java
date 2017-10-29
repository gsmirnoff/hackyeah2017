package com.hackaton.crawler;

import com.hackaton.crawler.allegro.AllegroCrawler;
import com.hackaton.crawler.http.HttpClient;
import com.hackaton.crawler.http.UnirestHttpClient;
import com.hackaton.crawler.model.Auction;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bronek on 10/29/17.
 */
@Component
public class CrawlerService {

    @Resource
    private CrawlerStorage storage;

    private static final List<String> UNEXPECTED_KEYWORDS = Arrays.asList(
            "STAN  technicznie nowy - 100% sprawny i wydajny",
            "niemal nowy",
            "Włączony kilkanaście razy",
            "Pierwotna data zakupu",
            "jak NOWY",
            "powystawowy",
            "Rynek wtórny"
    );
    private AllegroCrawler crawler = new AllegroCrawler();
    private boolean isRunning = false;

    public void resetCrawler() {
        isRunning = false;
    }

    public boolean triggerCrawler(int searchPage) throws IOException, DataFetcherException {
        if (isRunning) {
            System.out.println("Can't start! Crawler is already running!");
            return false;
        }
        System.out.println("Crawler started for page " + searchPage);
        isRunning = true;

        HttpClient httpClient = new UnirestHttpClient();
        crawler.setHttpClient(httpClient);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("stan", "nowe");
        parameters.put("order", "m");
        parameters.put("string", "marża");

        crawler.setSearchParameters(parameters);
        crawler.setUnexpectedWords(UNEXPECTED_KEYWORDS);

        final int auctionCount = 60;
        List<Auction> auctionList = crawler.fetch(auctionCount, searchPage);

        storage.add(auctionList);

        isRunning = false;
        System.out.println("Crawler finished.");
        return true;
    }

}
