package com.homefinder.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** The "skeleton web app": the smallest possible endpoint. Open http://localhost:8080/api/hello */
@RestController
public class HelloController {

    @GetMapping("/api/hello")
    public String hello() {
        return "HomeFinder API is running";
    }
}
