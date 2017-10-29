package com.hellyeah.export;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.*;

public class CSVMergerTest {

	private static final String CSV_DIR = "c:\\Development\\projects\\hackyeah\\";

	CSVMerger merger;

	@Before
	public void setUp() {
		CSVFormattedWriter writer = new CSVFormattedWriter();
		writer.setHeader("#nick;nip;email;phone,,");

		CSVWriter csvWriter = new CSVWriter();
		csvWriter.setDirectory(CSV_DIR);
		writer.setWriter(csvWriter);

		merger = new CSVMerger();
		merger.setFiles(Arrays.asList(
				Paths.get("c:\\Development\\projects\\hackyeah\\page 1-30.csv"),
				Paths.get("c:\\Development\\projects\\hackyeah\\page 31-50.csv"),
				Paths.get("c:\\Development\\projects\\hackyeah\\page 51-81.csv"),
				Paths.get("c:\\Development\\projects\\hackyeah\\page 81-100.csv")));
		merger.setWriter(writer);
	}


	@Test
	public void merge() throws Exception {
		merger.merge();
	}

}