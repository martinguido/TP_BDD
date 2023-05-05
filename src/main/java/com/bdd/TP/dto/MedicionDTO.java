package com.bdd.TP.dto;
import com.bdd.TP.dao.Region;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import lombok.Data;
import javax.persistence.*;
import java.util.Date;

//@Data
public class MedicionDTO {
    private Long id;

    private Double demanda;

    private Double temperatura;

    private Date fecha;

    private Region region;

    public MedicionDTO() {
    }

    public MedicionDTO(Long id, Double demanda, Double temperatura, Date fecha, Region region) {
        this.id = id;
        this.demanda = demanda;
        this.temperatura = temperatura;
        this.fecha = fecha;
        this.region = region;
    }

    public MedicionDTO(Date fecha, Region region, Double demanda, Double temperatura) {
        this.demanda = demanda;
        this.temperatura = temperatura;
        this.fecha = fecha;
        this.region = region;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDemanda() {
        return demanda;
    }

    public void setDemanda(Double demanda) {
        this.demanda = demanda;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }


}