package com.bdd.TP.processor;

import com.bdd.TP.dao.Medicion;
import com.bdd.TP.dao.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MedicionItemProcessor implements ItemProcessor<Medicion, Medicion> {
    private static final Logger log = LoggerFactory.getLogger(RegionItemProcessor.class);
    public Medicion process(Medicion medicion){
        final Region region = medicion.getRegion();
        System.out.println(region);
        final Double demanda = medicion.getDemanda();
        System.out.println(demanda);
        final Double temperatura = medicion.getTemperatura();
        System.out.println(temperatura);
        final Date fecha = medicion.getFecha();
        final Medicion transformedMedicion = new Medicion(demanda, temperatura, fecha, region);
        log.info("Converting (" + region + ") into (" + transformedMedicion + ")");
        return transformedMedicion;
    }

}