package com.hackaton.controller;

import com.hackaton.crawler.CrawlerStorage;
import com.hackaton.crawler.export.CSVFormattedWriter;
import com.hackaton.crawler.export.CSVWriter;
import com.hackaton.crawler.model.Auction;
import com.hackaton.cron.CrawlerTrigger;
import org.json.JSONException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.List;

/**
 * Created by bronek on 10/29/17.
 */
@RestController
public class CrawlerController {
    @Resource
    private CrawlerTrigger crawlerTrigger;

    @Resource
    private CrawlerStorage crawlerStorage;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() throws JSONException {
        return "test";
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public int count() throws JSONException, IOException {

        List<Auction> allUniques = crawlerStorage.getAllUniques();
        return allUniques.size();
    }

    @RequestMapping(value = "/resetPage", method = RequestMethod.GET)
    public String reset(@PathParam(value = "to") int to) throws JSONException, IOException {
        crawlerTrigger.resetPage(to);
        return "OK!";
    }

    @RequestMapping(value = "/resetTrigger", method = RequestMethod.GET)
    public String resetT() throws JSONException, IOException {
        crawlerTrigger.resetTrigger();
        return "OK!";
    }


    @RequestMapping(value = "/tofile", method = RequestMethod.GET)
    public String toFile() throws JSONException, IOException {

        CSVFormattedWriter writer = new CSVFormattedWriter();
        writer.setHeader("#nick;nip;email;phone,,");

        CSVWriter csvWriter = new CSVWriter();
        csvWriter.setDirectory("/tmp/");
        writer.setWriter(csvWriter);
        List<Auction> allUniques = crawlerStorage.getAllUniques();
        String s = writer.writeAll(allUniques);

        return "Yo, check file: " + s;
    }

    @RequestMapping(value = "/crawler", method = RequestMethod.GET)
    public List<Auction> gimmeAll() throws JSONException {
        return crawlerStorage.getAllUniques();
    }
}
