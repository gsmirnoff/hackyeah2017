package com.hellyeah.export;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.hellyeah.model.Auction;


public class CSVFormattedWriterTest {

	private static final String CSV_DIR = "c:\\Development\\projects\\hackyeah\\";

	private CSVFormattedWriter writer;

	@Before
	public void setUp() {
		writer = new CSVFormattedWriter();
		writer.setHeader("#nick;nip;email;phone,,");

		CSVWriter csvWriter = new CSVWriter();
		csvWriter.setDirectory(CSV_DIR);
		writer.setWriter(csvWriter);
	}

	@Test
	public void write() throws Exception {
		// given
		final List<Auction> auctions = Arrays.asList(
				new Auction().withNickname("smart_fon").withnIP("8992746964").withEmail("smartfon.allegro@gmail.com").withPhone("535468930").withPhone("888599879").withPhone("608092396"),
				new Auction().withNickname("KUBTEL_S").withEmail("meandmobileallegro@gmail.com").withPhone("509919099").withPhone("530222477"),
				new Auction().withNickname("MDM-KOM").withnIP("7272801172").withPhone("502928871"),
				new Auction().withNickname("www_PL-GSM_pl").withnIP("727-280-11-72")
		);

		// when
		writer.writeAll(auctions);

		// then
		//TODO make mock for CSVWriter and check write() method call
	}

}