package com.tryton.small_world.matcher.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeartbeatController {

    @GetMapping("/healthz")
    public ResponseEntity checkStatus() {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Custom-Header", "Awesome");

        return ResponseEntity.ok().headers(httpHeaders).build();
    }
}
