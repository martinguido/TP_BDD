package com.bdd.TP.controller;

import com.bdd.TP.dto.HelloWordResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class HelloWordController {
    @GetMapping("/hello")
    public HelloWordResponse helloWord(@RequestParam(value = "name", defaultValue = "Word") String name){
        return new HelloWordResponse("Hello " + name + "!");
    }

    @GetMapping("/name/{name}")
    public String helloName(@PathVariable("name") String name){
        return "Hello " + name +"!";
    }
}
