package com.bdd.TP.dao;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="MEDICIONES")
public class Medicion {
    @Id
    @Column(name="MY_ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name="DEMANDA")
    private Double demanda;
    @Column(name="TEMPERATURA")
    private Double temperatura;
    @Column(name="FECHA")
    private Date fecha;
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @JoinColumn(name="ID")
    private Region region;
    public Double getTemperatura() {
        return temperatura;
    }
    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public Medicion(){}
    public Medicion(Double demanda, Double temperatura, Date fecha, Region region) {
        this.demanda = demanda;
        this.temperatura = temperatura;
        this.fecha = fecha;
        this.region = region;
    }
    public String getId() {
        return id;
    }
    public Date getFecha() {
        return fecha;
    }
    public Double getDemanda() {
        return demanda;
    }
    public void setDemanda(Double demanda) {
        this.demanda = demanda;
    }
    public Region getRegion() {
        return region;
    }
    public void setRegion(Region region) {
        this.region = region;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medicion medicion = (Medicion) o;
        return Objects.equals(demanda, medicion.demanda) && Objects.equals(temperatura, medicion.temperatura) && Objects.equals(fecha, medicion.fecha) && Objects.equals(region, medicion.region);
    }
    @Override
    public int hashCode() {
        return Objects.hash(demanda, temperatura, fecha, region);
    }
}