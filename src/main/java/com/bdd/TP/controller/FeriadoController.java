package com.bdd.TP.controller;

import com.bdd.TP.dao.Feriado;
import com.bdd.TP.service.CammesaService;
import com.bdd.TP.service.FeriadoService;
import com.bdd.TP.service.MedicionService;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@RestController
@RequestMapping("api/v1")
public class FeriadoController {
    private final CammesaService cammesaService;
    private final FeriadoService feriadoService;
    private final MedicionService medicionService;

    public FeriadoController(CammesaService cammesaService, FeriadoService feriadoService, MedicionService medicionService) {
        this.cammesaService = cammesaService;
        this.feriadoService = feriadoService;
        this.medicionService = medicionService;
    }
    @GetMapping("/esFeriado")
    public String esFeriado(@RequestParam(value="fecha") String fecha){
        return cammesaService.esFeriado(fecha);
    }


    @GetMapping("/demandaFeriado")
    public void demandaFeriado(@RequestParam(value="fecha") String fecha) throws ParseException {
        Date fechaFormatoDate = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
        Feriado primerFeriadoEncontrado = feriadoService.feriadoCercano(fechaFormatoDate);
        Date fechaHardcodeada = new SimpleDateFormat("yyyy-MM-dd").parse("2023-04-20");
        double avgDemanda = medicionService.avgDemandaFechaEspecifica(fechaHardcodeada);
    }
    @PostMapping("/actualizarFeriados")
    public void actualizarFeriados(@RequestParam(value="fecha") String fecha) throws ParseException {
        Date date = new Date();
        Instant inst = date.toInstant();
        LocalDate localDate = inst.atZone(ZoneId.systemDefault()).toLocalDate();
        Instant dayInst = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Date day = Date.from(dayInst);
        String formattedNewDate = (new SimpleDateFormat("yyyy-MM-dd").parse(fecha)).toString();
        List<Feriado> listaFeriados = new ArrayList<Feriado>();
        int i = 0;
        while (! formattedNewDate.equals(day.toString())){
            String newDatePlus = LocalDate.parse(fecha).plusDays(i).toString();
            formattedNewDate = (new SimpleDateFormat("yyyy-MM-dd").parse(newDatePlus)).toString();
            Feriado feriado = new Feriado(new SimpleDateFormat("yyyy-MM-dd").parse(newDatePlus), Boolean.parseBoolean(cammesaService.esFeriado(newDatePlus)));
            i++;
            listaFeriados.add(feriado);
        }
        feriadoService.loadFeriados(listaFeriados);
    }
    @GetMapping("/demandaFeriadoMasCercano")
    public String demandaFeriadoMasCercano(@RequestParam(value="fecha") String fecha){
        return cammesaService.demandaFeriadoMasCercano(fecha);
    }


}