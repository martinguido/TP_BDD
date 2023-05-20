package com.bdd.TP.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;
@Entity
@Table(name="FERIADOS")
public class Feriado {
    @Id
    @Column(name="FECHA")
    private Date fecha;
    @Column(name="ESFERIADO")
    private boolean esFeriado;
    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public boolean getEsFeriado() {
        return esFeriado;
    }
    public void setEsFeriado(boolean esFeriado) {
        this.esFeriado = esFeriado;
    }
    public Feriado() {}
    public Feriado(Date fecha, boolean esFeriado) {
        this.fecha = fecha;
        this.esFeriado = esFeriado;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feriado feriado = (Feriado) o;
        return Objects.equals(fecha, feriado.fecha) && Objects.equals(esFeriado, feriado.esFeriado);
    }
    @Override
    public int hashCode() {
        return Objects.hash(fecha);
    }
    @Override
    public String toString() {
        return "Feriado{" +
                "fecha=" + fecha +
                ", esFeriado='" + esFeriado + '\'' +
                '}';
    }
}