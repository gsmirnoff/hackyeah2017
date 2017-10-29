package com.hackaton.crawler;


import com.hackaton.crawler.model.Auction;

import java.util.List;


public interface DataFetcher {

	List<Auction> fetch(int auctionsCount, int searchPage) throws DataFetcherException;

}
