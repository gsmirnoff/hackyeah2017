package com.hellyeah;

import com.hellyeah.model.Auction;

import java.util.List;


public interface DataFetcher {

	List<Auction> fetch(int auctionsCount, int startPage) throws DataFetcherException;

}
