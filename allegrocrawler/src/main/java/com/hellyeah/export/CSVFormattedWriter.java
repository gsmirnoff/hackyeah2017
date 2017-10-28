package com.hellyeah.export;

import com.hellyeah.allegro.AllegroCrawler;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


public class CSVFormattedWriter {

	private String header;

	private CSVWriter writer;

	public void writeAll(List<AllegroCrawler.AllegroAuction> auctions) throws IOException {
		List<String> lines = auctions.stream()
										.map(a -> format(a.getNickname(), a.getnIP(), a.getEmail(), a.getPhones()))
										.collect(Collectors.toList());
		writer.write(UUID.randomUUID().toString(), header, lines);
	}

	private String format(String nickname, String nIP, String email, List<String> phones) {
		return nickname + ";" + formatNIP(nIP) + ";" + formatEmail(email) + ";" + formatPhones(phones);
	}

	private String formatNIP(String nIP) {
		if (nIP == null) {
			return "";
		}
		return nIP.trim().replaceAll("[^\\d]+", "");
	}

	private String formatEmail(String email) {
		return email == null ? "" : email;
	}

	private String formatPhones(List<String> phones) {
		return (phones.isEmpty() ? "" : formatPhone(phones.get(0))) + "," + (phones.size() > 1 ? formatPhone(phones.get(1)) : "") + "," + (phones.size() > 2 ? formatPhone(phones.get(2)) : "");
	}

	private String formatPhone(String phone) {
		if (phone == null) {
			return "";
		}
		return phone.trim().replaceAll("[^\\d]+", "");
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public void setWriter(CSVWriter writer) {
		this.writer = writer;
	}

}
