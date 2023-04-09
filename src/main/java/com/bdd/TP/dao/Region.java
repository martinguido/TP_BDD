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
    private Integer id_region;
    @Column(name="NOMBRE_REGION")
    private String nombre;

    public Integer getId_region() {
        return id_region;
    }

    public void setId_region(Integer id_region) {
        this.id_region = id_region;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Region() {

    }
    public Region(Integer id_region, String nombre) {
        this.id_region = id_region;
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Region region = (Region) o;
        return Objects.equals(id_region, region.id_region) && Objects.equals(nombre, region.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_region);
    }

    @Override
    public String toString() {
        return "Region{" +
                "id_region=" + id_region +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}