package com.bdd.TP.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.*;
import java.time.LocalDate;

@Service
public class CammesaService {
    private RestTemplateBuilder restTemplateBuilder;
    // es una clase que te permite hacer requests a servicios

    CammesaService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public String esFeriado(String fecha) {
        return restTemplateBuilder
                .build()
                .getForObject("https://api.cammesa.com/demanda-svc/demanda/EsDiaFeriado?fecha=" + fecha, String.class);
    }

    public String demandaFeriadoMasCercano(String newDate) {
        int i = 1;
        // String newDate = date;
        while (Objects.equals(esFeriado(newDate), "false")) {
            newDate = LocalDate.parse(newDate).plusDays(i).toString();
            i = i + 1;
        }

        return newDate;
    }
    public String cammesaSalud() {
        return restTemplateBuilder
                .build()
                .getForObject("https://api.cammesa.com/demanda-svc/ping", String.class);
    }
    public List actualizarRegiones() {
        return restTemplateBuilder
                .build()
                .getForObject("https://api.cammesa.com/demanda-svc/demanda/RegionesDemanda", List.class);

    }
}
