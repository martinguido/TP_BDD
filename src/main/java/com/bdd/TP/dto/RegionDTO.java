package com.bdd.TP.dto;
//import lombok.Data;
//@Data
public class RegionDTO {
    public Long id;
    private Integer idRegion;
    private String nombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(Integer idRegion) {
        this.idRegion = idRegion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public RegionDTO(Long id, Integer idRegion, String nombre) {
        this.id = id;
        this.idRegion = idRegion;
        this.nombre = nombre;
    }
}
