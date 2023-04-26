package com.bdd.TP.dto;
import com.bdd.TP.dao.Region;
//import lombok.Data;
import java.util.Date;

//@Data
public class MedicionDTO {
    private Date fecha;
    private Region region;
    private Double demanda;
    private Double temperatura;
    public MedicionDTO(Date fecha, Region region, Double demanda, Double temperatura) {
        this.fecha = fecha;
        this.region = region;
        this.demanda = demanda;
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