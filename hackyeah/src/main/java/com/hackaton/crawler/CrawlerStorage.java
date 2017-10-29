package com.hackaton.crawler;

import com.hackaton.crawler.model.Auction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Created by bronek on 10/29/17.
 */
@Component
public class CrawlerStorage {
    private final CopyOnWriteArrayList<Auction> objectList = new CopyOnWriteArrayList<>();

    public boolean add(List<Auction> objects) {
        return objectList.addAll(objects);
    }

    public List<Auction> getAllUniques() {
        return objectList.stream().distinct().collect(Collectors.toList());
    }
}
