package com.hackaton.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/")
public class ListOrders {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(@PathParam(value = "number") Integer number) {
        return "Result is = " + number * 2;
    }

}
