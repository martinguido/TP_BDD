package com.bdd.TP.controller;
import com.bdd.TP.dao.Medicion;
import com.bdd.TP.dao.Region;
import com.bdd.TP.dto.MedicionDTO;
import com.bdd.TP.service.CammesaService;
import com.bdd.TP.service.MedicionService;
import com.bdd.TP.service.RegionService;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
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


        return "Successfully saved";
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