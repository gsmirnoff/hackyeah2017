package com.hackaton.crawler.http;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.Map;


public class UnirestHttpClient implements HttpClient {

	@Override
	public String get(String url, Map<String, Object> queryParameters) throws HttpClientException {
		//TODO add headers to mimic real user behaviors
		//TODO handle error responses
		HttpResponse<String> response;
		try {
			response = Unirest.get(url)
					.queryString(queryParameters)
					.asString();
		} catch (UnirestException e) {
			throw new HttpClientException("Unable to fetch URL " + url + " with params " + queryParameters, e);
		}
		return response.getBody();
	}

}
