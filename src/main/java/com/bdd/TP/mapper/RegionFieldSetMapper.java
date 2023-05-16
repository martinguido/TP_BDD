package com.bdd.TP.mapper;

import com.bdd.TP.dao.Medicion;
import com.bdd.TP.dao.Region;
import com.bdd.TP.dto.MedicionDTO;
import com.bdd.TP.exceptions.RegionDoesNotExistException;
import com.bdd.TP.service.MedicionService;
import com.bdd.TP.service.RegionService;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class RegionFieldSetMapper implements FieldSetMapper<Medicion> {

    private final RegionService regionService;
    private final MedicionService medicionService;

    public RegionFieldSetMapper(RegionService regionService, MedicionService medicionService) {
        this.regionService = regionService;
        this.medicionService = medicionService;
    }

    @Override
    public Medicion mapFieldSet(FieldSet fieldSet) {

        String temperaturaValue = fieldSet.readString("temperatura");
        Double temperatura;
        if (!temperaturaValue.equals("null")) {
            temperatura = Double.parseDouble(temperaturaValue);
        } else {
            temperatura = null;
        }

        Medicion newMedicion = medicionService.createMedicion(new MedicionDTO(fieldSet.readDate("fecha", "yyyy-MM-dd'T'HH:mm:ss.SSSZ"), convertToRegion(fieldSet.readString("id_region")), fieldSet.readDouble("demanda"), temperatura));
        return newMedicion;
    }

    private Region convertToRegion(String id_region) {

        int idRge = Integer.parseInt(id_region);
        Region savedRegion = regionService.getById(idRge);

        return savedRegion;
    }


}