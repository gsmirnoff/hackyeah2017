package com.hackaton.cron;

import com.hackaton.crawler.CrawlerService;
import com.hackaton.crawler.DataFetcherException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bronek on 10/29/17.
 */
@Component
public class CrawlerTrigger {

    @Resource
    private CrawlerService crawlerService;
    private int i = 1;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");

    public void resetPage(int newI) {
        i = newI;
    }

    public void resetTrigger() {
        crawlerService.resetCrawler();
    }

    @Scheduled(fixedRate = 10000)
    public void reportCurrentTime() throws IOException, DataFetcherException {
        System.out.println("Trying to run crawler " + i + " [" + dateFormat.format(new Date()));
        if (crawlerService.triggerCrawler(i)) {
            i++;
        }
    }
}
