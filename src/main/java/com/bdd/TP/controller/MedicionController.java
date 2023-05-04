package com.bdd.TP.controller;
import com.bdd.TP.dao.Feriado;
import com.bdd.TP.dao.Medicion;
import com.bdd.TP.dao.Region;
import com.bdd.TP.dto.MedicionDTO;
import com.bdd.TP.service.CammesaService;
import com.bdd.TP.service.MedicionService;
import com.bdd.TP.service.RegionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    public List<Medicion> actualizarDemanda(@RequestParam(value = "fecha") String fecha, @RequestParam(value = "idRge") Integer id_region) throws ParseException {
        Date newDate = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
        Optional<Region> region = regionService.findById(Long.valueOf(id_region));
        return medicionService.findByFechaAndRegion(newDate, region);
    }

    @PostMapping("/cammesa/actualizarMediciones")
    public List<Medicion>  actualizarMediciones(@RequestParam(value = "fecha") String fecha) throws ParseException {
        medicionService.deleteAllMediciones();
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
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String formattedDate = today.minusDays(j).format(formatter);
                List<HashMap<?, ?>> mediciones = cammesaService.demandaYTempertauraRegionPorFecha(formattedDate, (regiones.get(i)).getId());
                HashMap<String, Double> medicion = medicionService.sumarDemandayTemperaturaTotal(mediciones, formattedDate, (regiones.get(i)).getId());
                Medicion newMedicion = medicionService.createMedicion(new MedicionDTO(date, regiones.get(i), medicion.get("demanda"), medicion.get("temperatura")));
                j++;
                listaMediciones.add(newMedicion);
            }


        }
        medicionService.saveMediciones(listaMediciones);
        return listaMediciones;
    }

    @PostMapping("/cammesa/diaConMayorDemandaPorRegion")
    public List<Medicion> diaConMayorDemandaPorRegion() {

        return medicionService.dateWithMaxDemandByRegion();
    }
}