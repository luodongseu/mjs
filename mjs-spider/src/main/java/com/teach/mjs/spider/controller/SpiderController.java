package com.teach.mjs.spider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luodong
 */
@RestController
@RequestMapping("/spider")
public class SpiderController {

    @GetMapping("/d")
    public String demo() {
        return "Hello demo!";
    }
}
