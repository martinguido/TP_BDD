package com.bdd.TP.controller;
import com.bdd.TP.dao.Medicion;
import com.bdd.TP.dao.Region;
import com.bdd.TP.dto.MedicionDTO;
import com.bdd.TP.repository.MedicionRepository;
import com.bdd.TP.repository.RegionRepository;
import com.bdd.TP.service.CammesaService;
import com.bdd.TP.service.MedicionService;
import com.bdd.TP.service.RegionService;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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
    public List<Medicion> actualizarDemanda(@RequestParam(value="fecha") String fecha, @RequestParam(value = "idRge") Integer id_region) throws ParseException {
        Date newDate = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
        Optional<Region> region = regionService.findById(Integer.valueOf(id_region));
        return medicionService.findByFechaAndRegion(newDate, region);
    }

    @PostMapping("/cammesa/actualizarMediciones")
    public void actualizarMediciones() throws ParseException {
        List<Region> regiones = regionService.findAll();
//        Date newDate = new SimpleDateFormat("yyyy-MM-dd").parse("2023-04-01");
//        System.out.println(regiones);
        LocalDate today = LocalDate.now();
        for (int i=0; i<regiones.size(); i++){
            int j = 2;
            while (!(today.minusDays(j).equals(today))) {
                ZoneId zoneId = ZoneId.systemDefault();
                Date date = Date.from((today.minusDays(j)).atStartOfDay(zoneId).toInstant());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String formattedDate = today.minusDays(j).format(formatter);
//                System.out.println("----> "+formattedDate + regiones.get(i).getId());
                List<HashMap<?, ?>> mediciones  = cammesaService.demandaYTempertauraRegionPorFecha(formattedDate, (regiones.get(i)).getId());
                HashMap<String, Double> medicion =  medicionService.sumarDemandayTemperaturaTotal(mediciones,formattedDate, (regiones.get(i)).getId());
                medicionService.createMedicion(new MedicionDTO(date, regiones.get(i), medicion.get("demanda"), medicion.get("temperatura")));
                j--;
            }
            //i++;
        }
    }
}