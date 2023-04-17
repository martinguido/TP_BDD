package com.bdd.TP.dto;
import lombok.Data;
@Data
public class RegionDTO {
    public Long id;
    private Integer idRegion;
    private String nombre;

    public RegionDTO(Long id, Integer idRegion, String nombre) {
        this.id = id;
        this.idRegion = idRegion;
        this.nombre = nombre;
    }
}
