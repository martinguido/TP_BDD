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

        @Column(name="REGION_ID")
        private Integer region_id;

        @Column(name="FECHA")
        private Date fecha;

        @Column(name="DEMANDA")
        private Integer demanda;

        @Column(name="TEMPERATURA")
        private String temperatura;

        public DemandaYTemperatura() {
        }
        public DemandaYTemperatura(Integer region_id, Date fecha, Integer demanda, String temperatura) {
            this.region_id = region_id;
            this.fecha = fecha;
            this.demanda = demanda;
            this.temperatura = temperatura;
        }

        public Long getId() {
            return id;
        }
        public void setId(Long id) {
        this.id = id;
    }
        public Integer getRegionId() {
            return region_id;
        }
        public void setRegionId(Integer region_id) {this.region_id = region_id;}
        public Integer getDemanda() {
            return demanda;
        }
        public void setDemanda(Integer demanda) {
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
            return Objects.equals(region_id, dyt.region_id) && Objects.equals(fecha, dyt.fecha) && Objects.equals(demanda, dyt.demanda) && Objects.equals(temperatura, dyt.temperatura);
        }
        @Override
        public int hashCode() {
            return Objects.hash(region_id, fecha, demanda, temperatura);
        }
        @Override
        public String toString() {
            return "DemandaYTemperatura{" +
                    ", region_id=" + region_id +
                    ", fecha=" + fecha +
                    ", demanda=" + demanda +
                    ", temperatura='" + temperatura + '\'' +
                    '}';
        }


}
