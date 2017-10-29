package com.hackaton.crawler.http;

import java.util.Map;


public interface HttpClient {

	String get(String url, Map<String, Object> queryParameters) throws HttpClientException;

}
