package com.bdd.TP.dto;

import java.util.Date;

public class FeriadoDTO {
    private Date fecha;
    private boolean esFeriado;

    public FeriadoDTO() {
    }

    public FeriadoDTO(Date fecha, boolean esFeriado) {
        this.fecha = fecha;
        this.esFeriado = esFeriado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isEsFeriado() {
        return esFeriado;
    }

    public void setEsFeriado(boolean esFeriado) {
        this.esFeriado = esFeriado;
    }
}
