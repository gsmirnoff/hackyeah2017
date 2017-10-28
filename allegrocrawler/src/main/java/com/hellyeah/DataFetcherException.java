package com.hellyeah;


public class DataFetcherException extends Exception {

	public DataFetcherException(String message) {
		super(message);
	}

	public DataFetcherException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataFetcherException(Throwable cause) {
		super(cause);
	}
}
