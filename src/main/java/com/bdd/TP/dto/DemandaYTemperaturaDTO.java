package com.bdd.TP.dto;

import java.util.Date;

public class DemandaYTemperaturaDTO {

    private Integer region_id;
    private Date fecha;

    private Double demanda;

    private Double temperatura;

    public DemandaYTemperaturaDTO() {
    }

    public DemandaYTemperaturaDTO(Integer region_id, Date fecha, Double demanda, Double temperatura) {
        this.region_id = region_id;
        this.fecha = fecha;
        this.demanda = demanda;
        this.temperatura = temperatura;
    }

    public Integer getRegionId() {
        return region_id;
    }

    public void setRegionId(Integer region_id) {
        this.region_id = region_id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

}
