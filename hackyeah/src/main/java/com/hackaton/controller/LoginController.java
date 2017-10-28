package com.hackaton.controller;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class LoginController {
    private static final String TOKEN_URL = "https://ssl.allegro.pl/auth/oauth/token";
    private static final String CLIENT_ID = "a6d52468-b1db-46f3-b4fa-8e5cae6c8b72";
    private static final String CLIENT_SECRET = "EDZA7SxJCiH1RLceUwffZeKcYpL0ejvbvsxm2Htd4G50jKCR4vd2jj2Ej8kXb0lO";
    private static final String API_KEY = "eyJjbGllbnRJZCI6ImE2ZDUyNDY4LWIxZGItNDZmMy1iNGZhLThlNWNhZTZjOGI3MiJ9.1BbuqhfyjexdiFZ7M4-MiGZgOEmCeFCteC2CYjNWPAw=";
    private static final String REDIRECT_URL = "http://127.0.0.1:8080/login";
    private static final String GRANT_TYPE = "authorization_code";

    @Resource
    private RestTemplate restTemplate;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String consumeToken(@PathParam(value = "code") String code) {
        System.out.println("CODE RECEIVED=" + code);

        HttpHeaders headers = buildAuthHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> exchange = restTemplate.exchange(TOKEN_URL + "?grant_type=authorization_code&code=" +
                code + "&api-key=" + API_KEY + "&redirect_uri=" + REDIRECT_URL, HttpMethod.POST, entity, String.class);

        return exchange.toString();


    }

    private HttpHeaders buildAuthHeaders() {
        String plainCreds = CLIENT_ID + ":" + CLIENT_SECRET; // clientId:clientSecret

        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);

        return headers;
    }

    @RequestMapping(value = "/get_all", method = RequestMethod.GET)
    public String getAll() {
        return "success!";
    }
}
