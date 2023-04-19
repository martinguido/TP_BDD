package com.bdd.TP.controller;

import com.bdd.TP.dao.Feriado;
import com.bdd.TP.service.CammesaService;
import com.bdd.TP.service.FeriadoService;
import jakarta.annotation.PostConstruct;
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

    public FeriadoController(CammesaService cammesaService, FeriadoService feriadoService) {
        this.cammesaService = cammesaService;
        this.feriadoService = feriadoService;
    }
//    @PostConstruct
    @PostMapping("/cammesa/actualizarFeriados")
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
//            System.out.println(feriado);
            //feriadoService.createFeriado(new FeriadoDTO(feriado.getFecha(), feriado.getEsFeriado()));
            i++;
            listaFeriados.add(feriado);
        }
//        System.out.println("********************************");
//        System.out.println(listaFeriados.toString());
        feriadoService.loadFeriados(listaFeriados);
    }

}