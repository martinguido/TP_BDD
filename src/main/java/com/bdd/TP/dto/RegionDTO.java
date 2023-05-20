package com.bdd.TP.dto;

public class RegionDTO {
    private Integer id;
    private Integer idElemento;

    private Integer idPadre;

    private Integer idRge;

    private String nombre;

    public RegionDTO() {
    }

    public RegionDTO(Integer id, Integer idElemento, Integer idPadre, Integer idRge, String nombre) {
        this.id = id;
        this.idElemento = idElemento;
        this.idPadre = idPadre;
        this.idRge = idRge;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdElemento() {
        return idElemento;
    }

    public void setIdElemento(Integer idElemento) {
        this.idElemento = idElemento;
    }

    public Integer getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(Integer idPadre) {
        this.idPadre = idPadre;
    }

    public Integer getIdRge() {
        return idRge;
    }

    public void setIdRge(Integer idRge) {
        this.idRge = idRge;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
