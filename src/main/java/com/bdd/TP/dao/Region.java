package com.bdd.TP.dao;

import jakarta.persistence.*;

import java.util.Objects;
@Entity
@Table(name="REGIONES")
public class Region {
//    @Id
//    @Column(name="ID")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    @Id
    @Column(name="ID_REGION")
    private Integer idRegion;
    @Column(name="NOMBRE_REGION")
    private String nombre;

    public Integer getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(Integer id_region) {
        this.idRegion = id_region;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Region() {

    }
    public Region(Integer idRegion, String nombre) {
        this.idRegion = idRegion;
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Region region = (Region) o;
        return Objects.equals(idRegion, region.idRegion) && Objects.equals(nombre, region.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRegion);
    }

    @Override
    public String toString() {
        return "Region{" +
                "id_region=" + idRegion +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}