package com.bdd.TP.dto;
import lombok.Data;
@Data
public class RegionDTO {
    private Integer id_region;
    private String nombre;

    public RegionDTO(Integer id_region, String nombre) {
        this.id_region = id_region;
        this.nombre = nombre;
    }
}
