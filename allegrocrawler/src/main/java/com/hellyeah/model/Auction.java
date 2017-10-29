package com.hellyeah.model;

import com.hellyeah.allegro.AllegroCrawler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Auction {

	private String url;
	private String nickname;
	private String nIP;
	private String email;
	private List<String> phones = new ArrayList<>();

	private Map<String, String> parameters;

	public String getNickname() {
		return nickname;
	}

	public String getnIP() {
		return nIP;
	}

	public String getEmail() {
		return email;
	}

	public List<String> getPhones() {
		return phones;
	}

	public Auction withNickname(String nickname) {
		this.nickname = nickname;
		return this;
	}

	public Auction withnIP(String nIP) {
		this.nIP = nIP;
		return this;
	}

	public Auction withEmail(String email) {
		this.email = email;
		return this;
	}

	public Auction withPhone(String phone) {
		this.phones.add(phone);
		return this;
	}

	public Auction withPhones(List<String> phones) {
		this.phones.addAll(phones);
		return this;
	}

	@Override
	public String toString() {
		return "Auction{" + "nickname='" + nickname + '\'' + ", nIP='" + nIP + '\'' + ", email='" + email + '\'' + ", phones=" + phones + '}';
	}
}
