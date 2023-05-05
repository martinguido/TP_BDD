package com.bdd.TP.controller;
import com.bdd.TP.dao.Medicion;
import com.bdd.TP.dao.Region;
import com.bdd.TP.dto.MedicionDTO;
import com.bdd.TP.service.CammesaService;
import com.bdd.TP.service.MedicionService;
import com.bdd.TP.service.RegionService;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("api/v1")
public class MedicionController {
    private final MedicionService medicionService;
    private final CammesaService cammesaService;
    private final RegionService regionService;

    public MedicionController(MedicionService medicionService, CammesaService cammesaService, RegionService regionService) {
        this.medicionService = medicionService;
        this.cammesaService = cammesaService;
        this.regionService = regionService;
    }

    //    @PostConstruct
    @PostMapping("/cammesa/demandaYTemperaturaDiario")
    public String actualizarDemanda(@RequestParam(value = "fecha") String fecha, @RequestParam(value = "id_region") Integer id_region) throws ParseException {
        Date newDate = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
        String response = "Already exists";
        List<HashMap<?, ?>> mediciones = cammesaService.demandaYTempertauraRegionPorFecha(fecha, id_region); //Mediciones de la fecha y region
        HashMap<String, Double> medicion = medicionService.sumarDemandayTemperaturaTotal(mediciones); //Medicion Total de una fecha y region
        Region region = regionService.getById(id_region);
        if (!medicionService.existeMedicion(newDate, Optional.ofNullable(region))) {
            Medicion newMedicion = medicionService.createMedicion(new MedicionDTO(newDate, region, medicion.get("demanda"), medicion.get("temperatura")));
            medicionService.saveMedicion(newMedicion);
            response = "OK";
                    }
        return response;
    }

    @PostMapping("/cammesa/actualizarMediciones")
    public void actualizarMediciones(@RequestParam(value = "fecha") String fecha) throws ParseException {
        //medicionService.deleteAllMediciones();
        List<Region> regiones = regionService.findAll();
        LocalDate today = LocalDate.now();
        Date formattedNewDate = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
        LocalDate fechaParametro = formattedNewDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        List<Medicion> listaMediciones = new ArrayList<>();
        for (int i = 0; i < regiones.size(); i++) {
            int j = 0;
                while (!(today.minusDays(j).equals(fechaParametro.minusDays(1)))) {

                    ZoneId zoneId = ZoneId.systemDefault();
                    Date date = Date.from((today.minusDays(j)).atStartOfDay(zoneId).toInstant());
                    if (!medicionService.existeMedicion(date, Optional.ofNullable(regiones.get(i)))) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String formattedDate = today.minusDays(j).format(formatter);
                    System.out.println("----> " + formattedDate + regiones.get(i).getId());
                    List<HashMap<?, ?>> mediciones = cammesaService.demandaYTempertauraRegionPorFecha(formattedDate, (regiones.get(i)).getId());
                    HashMap<String, Double> medicion = medicionService.sumarDemandayTemperaturaTotal(mediciones);
                    Medicion newMedicion = medicionService.createMedicion(new MedicionDTO(date, regiones.get(i), medicion.get("demanda"), medicion.get("temperatura")));
                    listaMediciones.add(newMedicion);
                }
                    j++;
            }

        }
        medicionService.saveMediciones(listaMediciones);

    }

    @GetMapping("/cammesa/diaConMayorDemandaPorRegion")
    public List<Medicion> diaConMayorDemandaPorRegion() {

        return medicionService.dateWithMaxDemandByRegion();
    }
}