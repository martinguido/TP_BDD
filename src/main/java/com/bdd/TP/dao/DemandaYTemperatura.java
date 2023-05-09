package com.bdd.TP.dao;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="demanda_y_temperatura_data")
public class DemandaYTemperatura {

        @Id
        @Column(name="ID")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name="REGION")
        private Integer region;

        @Column(name="FECHA")
        private Date fecha;

        @Column(name="DEMANDA")
        private String demanda;

        @Column(name="TEMPERATURA")
        private String temperatura;

        public DemandaYTemperatura() {
        }
        public DemandaYTemperatura(Integer region, Date fecha, String demanda, String temperatura) {
            this.region = region;
            this.fecha = fecha;
            this.demanda = demanda;
            this.temperatura = temperatura;
        }

        public Long getId() {return id;}
        public void setId(Long id) {
        this.id = id;
    }
        public Integer getRegion() {
            return region;
        }
        public void setRegion(String id_region) throws Exception {
            this.region = Integer.parseInt(id_region);
        }
        public String getDemanda() {
            return demanda;
        }
        public void setDemanda(String demanda) {
            this.demanda = demanda;
        }
        public String getTemperatura() {
            return temperatura;
        }
        public void setTemperatura(String temperatura) {this.temperatura = temperatura;}
        public void setFecha(String dateString) throws Exception {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            this.fecha = format.parse(dateString);
        }
        public String getFecha() {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            return format.format(this.fecha);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DemandaYTemperatura dyt = (DemandaYTemperatura) o;
            return Objects.equals(region, dyt.region) && Objects.equals(fecha, dyt.fecha) && Objects.equals(demanda, dyt.demanda) && Objects.equals(temperatura, dyt.temperatura);
        }
        @Override
        public int hashCode() {
            return Objects.hash(region, fecha, demanda, temperatura);
        }
        @Override
        public String toString() {
            return "DemandaYTemperatura{" +
                    ", region=" + region +
                    ", fecha=" + fecha +
                    ", demanda=" + demanda +
                    ", temperatura='" + temperatura + '\'' +
                    '}';
        }


}
