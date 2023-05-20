package com.bdd.TP.processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;
import com.bdd.TP.dao.Region;

public class RegionItemProcessor implements ItemProcessor<Region, Region> {
    private static final Logger log = LoggerFactory.getLogger(RegionItemProcessor.class);
    public Region process(final Region region){
        final Integer id = region.getId();
        final Integer idElemento = region.getIdElemento();
        final Integer idPadre = region.getIdPadre();
        final Integer idRge = region.getIdRge();
        final String nombre = region.getNombre().toUpperCase();
        final Region transformedRegion = new Region(id, idElemento, idPadre,idRge,nombre);
        log.info("Converting (" + region + ") into (" + transformedRegion + ")");
        return transformedRegion;
    }

}