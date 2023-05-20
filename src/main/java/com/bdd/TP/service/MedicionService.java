package com.bdd.TP.service;

import com.bdd.TP.dao.Medicion;
import com.bdd.TP.dao.Region;
import com.bdd.TP.dto.MedicionDTO;
import com.bdd.TP.exceptions.RegionDoesNotExistException;
import com.bdd.TP.repository.MedicionRepository;
import com.bdd.TP.repository.RegionRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class MedicionService {
    private final MedicionRepository medicionRepository;
    private final RegionRepository regionRepository;
    public MedicionService(MedicionRepository medicionRepository, RegionRepository regionRepository) {
        this.medicionRepository = medicionRepository;
        this.regionRepository = regionRepository;
    }
    public Medicion createMedicion(MedicionDTO medicionDTO) {
        Region region = medicionDTO.getRegion();
        Integer idRge = region.getId();
        Region savedRegion = regionRepository.findById(idRge)
                .orElseThrow(
                        ()-> new RegionDoesNotExistException("The region with id: " + idRge + " does not exists")
                );
        medicionDTO.setRegion(savedRegion);
        Medicion aNewMedicion = new Medicion(medicionDTO.getDemanda(),medicionDTO.getTemperatura(), medicionDTO.getFecha(), medicionDTO.getRegion());
        return aNewMedicion;
    }
    public boolean existeMedicion(Date fecha, Optional<Region> region){
        Optional<Medicion> medicion = medicionRepository.findByFechaAndRegion(fecha, region);
        return medicion.isPresent();
    }
    public double avgDemandaFechaEspecifica(Date fecha){
        double demandaEsteDia = medicionRepository.findSomeDateAvgDemand(fecha);
        return  demandaEsteDia;
    }
    public List<Object[]> dateWithMaxDemandByRegion() {
        return medicionRepository.dateWithMaxDemandByRegion();
    }
    public void saveMediciones(List<Medicion> listaMediciones) {
        medicionRepository.saveAll(listaMediciones);
    }
    public void deleteAll() {medicionRepository.deleteAll();}
}