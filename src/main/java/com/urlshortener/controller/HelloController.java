package com.urlshortener.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello URL Shortener";
    }
    
    @GetMapping("/about")
    public String about() {
    	return "This is my first Spring boot backend application";
    }
    

}