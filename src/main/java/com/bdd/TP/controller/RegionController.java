package com.bdd.TP.controller;

import com.bdd.TP.dao.Region;
import com.bdd.TP.service.CammesaService;
import com.bdd.TP.service.RegionService;
import jakarta.annotation.PostConstruct;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

//    @PostMapping("/cammesa/actualizarRegiones2")
//    public List actualizarRegiones2() {
//        List<HashMap<String, ?>> todasLasRegiones = cammesaService.actualizarRegiones2();
//        for (int i = 0; i < todasLasRegiones.size(); i++) {
//            regionService.createRegion(new RegionDTO((Integer) (todasLasRegiones.get(i)).get("id"),
//                    (String) ((todasLasRegiones.get(i)).get("nombre"))));
//        }
//        return todasLasRegiones;
//    }
//    @PostConstruct
    @PostMapping("/cammesa/actualizarRegiones")
    public List actualizarRegiones() {
        regionService.deleteAllRegions();
        List<Region> todasLasRegiones = cammesaService.actualizarRegiones();
        regionService.saveRegiones(todasLasRegiones);
        return todasLasRegiones;
    }
    @DeleteMapping("/cammesa/borrarRegion")
    public ResponseEntity<?> deleteMapping(@RequestParam(value="id_region") Integer idRge){
//        regionService.deleteRegion(regionService.getElementByIdRge(idRge));
//        return true;
        try {
            regionService.deleteRegion(regionService.getById(idRge));
            return ResponseEntity.ok("La region con idRge: "+idRge+" ha sido eliminada correctamente.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La region con idRge: " + idRge + " no existe.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
