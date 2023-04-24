package com.bdd.TP.dao;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name="REGIONES")
public class Region {
    /*@Id
    @Column(name="MY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long myId;*/
    @Id
    @Column(name="ID")
    private Integer id;
    @Column(name="ID_ELEMENTO")
    private Integer idElemento;
    @Column(name="ID_PADRE")
    private Integer idPadre;
    @Column(name="ID_RGE")
    private Integer idRge;
    @Column(name="NOMBRE")
    private String nombre;
    public Region() {
    }
    public Region(Integer id, Integer idElemento, Integer idPadre,Integer idRge, String nombre) {
        this.id = id;
        this.idElemento = idElemento;
        this.idPadre = idPadre;
        this.idRge = idRge;
        this.nombre = nombre;
    }
    /*public Long getMyId() {
        return myId;
    }
    public void setMyId(Long myId) {
        this.myId = myId;
    }*/
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getIdElemento() {
        return idElemento;
    }
    public void setIdElemento(Integer idElemento) {
        this.idElemento = idElemento;
    }
    public Integer getIdPadre() {
        return idPadre;
    }
    public void setIdPadre(Integer idPadre) {
        this.idPadre = idPadre;
    }
    public Integer getIdRge() {
        return idRge;
    }
    public void setIdRge(Integer idRge) {
        this.idRge = idRge;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Region region = (Region) o;
        return Objects.equals(id, region.id) && Objects.equals(idElemento, region.idElemento) && Objects.equals(idPadre, region.idPadre) && Objects.equals(idRge, region.idRge) && Objects.equals(nombre, region.nombre);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, idElemento, idPadre, idRge, nombre);
    }
    @Override
    public String toString() {
        return "Region{" +
                ", id=" + id +
                ", idElemento=" + idElemento +
                ", idPadre=" + idPadre +
                ", idRge=" + idRge +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}