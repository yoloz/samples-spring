package com.yoloz.example.helloworld.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yoloz
 */
@RestController
public class HelloWorldController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "Hello World!";
    }

    @RequestMapping(value = "/say", method = RequestMethod.GET)
    public String say(@RequestParam("name") String name) {
        return "Hello= " + name;
    }
}
