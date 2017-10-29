package com.hellyeah.allegro;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hellyeah.DataFetcherException;
import com.hellyeah.http.HttpClientException;

public class SearchDataCrawler extends AllegroCrawler {

	private static final Pattern PATTERN = Pattern.compile("SearchPageData\\{url='([^']+)', sellerId='([^']+)'\\}");

	private Path path;

	@Override
	protected String search(String page) throws HttpClientException {
		return null;
	}

	@Override
	protected List<SearchPageData> getSearchPageData(String searchPage) throws DataFetcherException {
		List<String> searchDataStr;
		try {
			searchDataStr = Files.readAllLines(path);
		} catch (IOException e) {
			throw new DataFetcherException("Unable to read search data file " + path.toString(), e);
		}

		List<SearchPageData> searchPageData = new ArrayList<>();
		for (String line : searchDataStr) {
			Matcher m = PATTERN.matcher(line);

			while(m.find()) {
				searchPageData.add(new SearchPageData(m.group(1), m.group(2)));
			}

		}
		return searchPageData;
	}

	public void setPath(Path path) {
		this.path = path;
	}
}
