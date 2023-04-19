package com.bdd.TP.dto;
import com.bdd.TP.dao.Region;
import lombok.Data;
import java.util.Date;

@Data
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
}