package com.bdd.TP.controller;

import com.bdd.TP.dao.Region;
import com.bdd.TP.service.CammesaService;
import com.bdd.TP.service.RegionService;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api")
public class RegionController {
    private final CammesaService cammesaService;
    private final RegionService regionService;
    public RegionController(CammesaService cammesaService, RegionService regionService) {
        this.cammesaService = cammesaService;
        this.regionService = regionService;
    }
    @PostMapping("/actualizarRegiones")
    public List actualizarRegiones() {
        regionService.deleteAllRegions();
        List<Region> todasLasRegiones = cammesaService.actualizarRegiones();
        regionService.saveRegiones(todasLasRegiones);
        return todasLasRegiones;
    }
    @DeleteMapping("/borrarRegion")
    public ResponseEntity<?> deleteMapping(@RequestParam(value="id_region") Integer idRge){
        try {
            regionService.deleteRegion(regionService.getById(idRge));
            return ResponseEntity.ok("La region con id: "+idRge+" ha sido eliminada correctamente.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La region con id: " + idRge + " no existe.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}