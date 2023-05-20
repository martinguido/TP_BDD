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
import java.util.*;

@RestController
@RequestMapping("api")
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
    @PostMapping("/demandaYTemperaturaDiario")
    public String demandaYTemperaturaDiario(@RequestParam(value = "fecha") String fecha, @RequestParam(value = "id_region") Integer id_region) throws ParseException {
        Date newDate = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
        List<HashMap<?,?>> mediciones = cammesaService.demandaYTempertauraRegionPorFecha(fecha, id_region); //Mediciones de la fecha y region
        Region region = regionService.getById(id_region);
        List<Medicion> listaMediciones = new ArrayList<>();
        double dem = 0.0;
        for (HashMap<?,?> medicion : mediciones) {
            if (!medicionService.existeMedicion(newDate, Optional.ofNullable(region)))
            {
                Medicion newMedicion = medicionService.createMedicion(new MedicionDTO(newDate, region, dem + (Integer) medicion.get("dem"), (Double) medicion.get("temp")));
                listaMediciones.add(newMedicion);
            }
        }
        medicionService.saveMediciones(listaMediciones);
        return "Guardado correctamente!";
    }


    @GetMapping("/diaConMayorDemandaPorRegion")
    public List<Map<String, Object>> diaConMayorDemandaPorRegion() {
        List<Object[]> results = medicionService.dateWithMaxDemandByRegion();
        List<Map<String, Object>> demandaByRegionList = new ArrayList<>();
        for (Object[] row : results) {
            int idRegion = (int) row[0];
            String fecha = row[1].toString();
            double demanda = (double) row[2];
            Map<String, Object> demandaByRegion = new HashMap<>();
            demandaByRegion.put("id_region", idRegion);
            demandaByRegion.put("fecha", fecha);
            demandaByRegion.put("demanda", demanda);
            demandaByRegionList.add(demandaByRegion);
        }
        return demandaByRegionList;
    }
}