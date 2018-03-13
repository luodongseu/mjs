package com.teach.mjs.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luodong
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/d")
    public String demo() {
        return "Hello demo!";
    }
}
