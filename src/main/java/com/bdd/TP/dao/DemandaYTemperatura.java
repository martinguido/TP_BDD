package com.bdd.TP.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class DemandaYTemperatura {

        private Integer region_id;

        private Date fecha;

        private Integer demanda;

        private Double temperatura;

        public DemandaYTemperatura() {
        }
        public DemandaYTemperatura(Integer region_id, Date fecha, Integer demanda, Double temperatura) {
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
        public Date getFecha() {return fecha;}
        public void setFecha(Date fecha) {this.fecha = fecha;}
        public Integer getDemanda() {
            return demanda;
        }
        public void setDemanda(Integer demanda) {
            this.demanda = demanda;
        }
        public Double getTemperatura() {
            return temperatura;
        }
        public void setTemperatura(Double temperatura) {
            this.temperatura = temperatura;
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
