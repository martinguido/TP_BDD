package com.bdd.TP.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("api")
public class HealthController {
    public HealthController() {}
    @GetMapping("/health")
    public ResponseEntity<String> health(){
        return new ResponseEntity<>("200 OK", HttpStatus.OK);
    }
}