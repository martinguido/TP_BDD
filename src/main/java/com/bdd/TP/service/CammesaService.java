package com.bdd.TP.service;

import com.bdd.TP.dao.Region;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import java.util.*;
import java.time.LocalDate;

@Service
public class CammesaService {
    private RestTemplateBuilder restTemplateBuilder;
    CammesaService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }
    public String esFeriado(String fecha) {
        return restTemplateBuilder
                .build()
                .getForObject("https://api.cammesa.com/demanda-svc/demanda/EsDiaFeriado?fecha=" + fecha, String.class);
    }

    public List<Region> actualizarRegiones() {
        List<Region> regionList = Arrays.stream(Objects.requireNonNull(restTemplateBuilder
                .build()
                .getForObject("https://api.cammesa.com/demanda-svc/demanda/RegionesDemanda",Region[].class))).toList();
        return regionList;
    }
    public List demandaYTempertauraRegionPorFecha(String fecha, Integer id_region) {
        return restTemplateBuilder
                .build()
                .getForObject("https://api.cammesa.com/demanda-svc/demanda/ObtieneDemandaYTemperaturaRegionByFecha?fecha="
                        + fecha + "&id_region=" + id_region, List.class);
    }
}