package com.bdd.TP.dto;

import lombok.Data;
import java.util.Date;

@Data
public class FeriadoDTO {
    private Date fecha;
    private boolean esFeriado;
    public FeriadoDTO(Date fecha, boolean esFeriado) {
        this.fecha = fecha;
        this.esFeriado = esFeriado;
    }
}
