package com.hackaton.client;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
public class AllegroPageClient implements PageClient {

    @Resource
    private RestTemplate restTemplate;

    @Override
    public int login(int testValue) {
        return testValue * 2;
    }

    @Override
    public void getOrders() {

    }
}
