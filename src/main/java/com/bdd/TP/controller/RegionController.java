package com.bdd.TP.controller;

import com.bdd.TP.dao.Region;
import com.bdd.TP.dto.RegionDTO;
import com.bdd.TP.service.CammesaService;
import com.bdd.TP.service.RegionService;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class RegionController {
    private final CammesaService cammesaService;
    private final RegionService regionService;

    public RegionController(CammesaService cammesaService, RegionService regionService) {
        this.cammesaService = cammesaService;
        this.regionService = regionService;
    }

//    @PostMapping("/region/")
//    public RegionDTO createRegion(@RequestBody RegionDTO regionDTO) {
//        return regionService.createRegion(regionDTO);
//    }

    @PostMapping("/cammesa/actualizarRegiones")
    public boolean actualizarRegiones() {
        List<HashMap<String, ?>> todasLasRegiones = cammesaService.actualizarRegiones();
        for (int i = 0; i < todasLasRegiones.size(); i++) {
            regionService.createRegion(new RegionDTO((Integer) (todasLasRegiones.get(i)).get("id"),
                    (String) ((todasLasRegiones.get(i)).get("nombre"))));
        }
        return true;
    }

}
