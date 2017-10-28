package com.hellyeah.export;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class CSVWriterTest {

	private static final String CSV_DIR = "c:\\Development\\projects\\hackyeah\\";

	private CSVWriter writer;

	@Before
	public void setUp() {
		writer = new CSVWriter();
		writer.setDirectory(CSV_DIR);
	}

	@Test
	public void write() throws Exception {
		// given
		final String fileName = "test";
		final String header = "#nick;nip;email;phone,,";
		final List<String> lines = Arrays.asList("all_elektrocity;6462831307;allegro@elektrocity.eu;608092396,,", "www_PL-GSM_pl;8952031414;sklep@pl-gsm.pl;,,");

		// when
		writer.write(fileName, header, lines);

		// then
		//TODO check result file
	}

}