package com.hackaton.crawler.export;

import com.hackaton.crawler.model.Auction;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class CSVFormattedWriter {

    private String header;

    private CSVWriter writer;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd'T'HH:mm:ss.SSS");

    public String writeAll(List<Auction> auctions) throws IOException {
        List<String> lines = auctions.stream()
                .map(a -> format(a.getNickname(), a.getnIP(), a.getEmail(), a.getPhones()))
                .collect(Collectors.toList());
        String fileName = "frauds_" + dateFormat.format(new Date());
        writer.write(fileName, header, lines);
        return fileName;
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
