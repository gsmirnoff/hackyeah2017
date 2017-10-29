package com.hellyeah.export;

import com.hellyeah.model.Auction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class CSVMerger {

	private static final Logger LOGGER = LoggerFactory.getLogger(CSVMerger.class);

	private CSVFormattedWriter writer;
	private List<Path> files;

	public void merge() throws IOException {
		List<Auction> resultAuctions = new ArrayList<>();
		for (Path file : files) {
			List<String> lines = Files.readAllLines(file);
			for (String line : lines) {
				if (line.startsWith("#")) {
					continue;
				}

				String[] objects = line.split(";");
				resultAuctions.add(new Auction()
									.withNickname(objects[0])
									.withnIP(objects[1])
									.withEmail(objects[2])
									.withPhones(Arrays.asList(objects[3].split(","))));
			}
		}

		List<Auction> result = new ArrayList<>();
		Map<String, List<String>> auctionsMap = new HashMap<>();
		for (Auction auction : resultAuctions) {
			List<String> phones = auctionsMap.get(auction.getNickname());

			if (phones == null) {
				if (!auction.getnIP().matches("\\d{10}") && !auction.getnIP().isEmpty()) {
					auction.withnIP("");

					LOGGER.info("Remove NIP " + auction.getnIP() + " for nickname " + auction.getNickname());
				}

				if (!auction.getEmail().isEmpty() && !auction.getEmail().matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
					LOGGER.info("Remove email " + auction.getEmail() + " for nickname " + auction.getNickname());
					auction.withEmail("");
				}

				if (!auction.getPhones().isEmpty()) {
					for (int i = 0; i < auction.getPhones().size() ; i++ ) {
						if (!auction.getPhones().get(i).matches("\\d{9}") && !"".equals(auction.getPhones().get(i)) && auction.getPhones().get(i).startsWith("48")) {
							LOGGER.info("Remove 48 prefix from phone in " + auction.getPhones());
							auction.getPhones().set(i, auction.getPhones().get(i).substring(2));
						}

						if (!auction.getPhones().get(i).matches("\\d{9,11}") && !"".equals(auction.getPhones().get(i))) {
							LOGGER.info("Remove invalid phone from phone in " + auction.getPhones());
							auction.getPhones().set(i, "");
						}

					}
				}

				result.add(auction);

				auctionsMap.put(auction.getNickname(), auction.getPhones());
			} else {
				LOGGER.warn("Duplication found for nickname " + auction.getNickname());

				for (String phone : auction.getPhones()) {
					if (!phones.contains(phone)) {
						LOGGER.warn("new phone " + phone + " (not in phones " + phones + ") appeared for nickname " + auction.getNickname());
					}
				}
			}
		}

		writer.writeAll(result);
	}

	public void setWriter(CSVFormattedWriter writer) {
		this.writer = writer;
	}

	public void setFiles(List<Path> files) {
		this.files = files;
	}
}
