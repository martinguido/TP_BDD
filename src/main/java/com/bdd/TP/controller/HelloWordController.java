package com.bdd.TP.controller;

import com.bdd.TP.dto.HelloWordResponse;
import com.bdd.TP.dao.Region;
import com.bdd.TP.repository.RegionRepository;
import com.bdd.TP.service.CammesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/v1")
public class HelloWordController {
    private final CammesaService cammesaService;

    public HelloWordController(CammesaService cammesaService) {
        this.cammesaService = cammesaService;
    }

    @GetMapping("/hello")
    public HelloWordResponse helloWord(@RequestParam(value = "name", defaultValue = "Word") String name){
        return new HelloWordResponse("Hello " + name + "!");
    }

    @GetMapping("/name/{name}")
    public String helloName(@PathVariable("name") String name){
        return "Hello " + name +"!";
    }

    @GetMapping("/cammesa/esFeriado")
    public String esFeriado(@RequestParam(value="fecha") String fecha){
        return cammesaService.esFeriado(fecha);
    }

    @GetMapping("/cammesa/demandaFeriadoMasCercano")
    public String demandaFeriadoMasCercano(@RequestParam(value="fecha") String fecha){
        return cammesaService.demandaFeriadoMasCercano(fecha);
    }

    @GetMapping("/health")
//    @ResponseStatus(code = HttpStatus.OK, reason = "OK")
    public ResponseEntity<String> cammesaSalud(){
        return new ResponseEntity<>("200 OK", HttpStatus.OK);
        //return "200 - OK";
    }

}
