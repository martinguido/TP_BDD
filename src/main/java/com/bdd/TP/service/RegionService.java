package com.bdd.TP.service;

import com.bdd.TP.dao.Feriado;
import com.bdd.TP.dao.Region;
import com.bdd.TP.dto.RegionDTO;
import com.bdd.TP.repository.RegionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionService {

    private final RegionRepository regionRepository;

    public RegionService(RegionRepository regionRepository){
        this.regionRepository = regionRepository;
    };
//    public RegionDTO createRegion(RegionDTO regionDTO){
//        Region aNewRegion = new Region(regionDTO.getIdRegion(), regionDTO.getNombre());
//        regionRepository.save(aNewRegion);
//        System.out.println(regionRepository.findAll().toString());
//        return regionDTO;
//    }
//    public List<Region> createAllRegion(List<Region> allRegions){
//        regionRepository.saveAll(allRegions);
//        return allRegions;
//    }
    public void deleteRegion(Region region){
        regionRepository.delete(region);
    }

    public Region getElementByIdRge(Integer idRge){
        return  regionRepository.getByIdRge(idRge);
    }

    public void saveRegiones(List<Region> todasLasRegiones) {
        regionRepository.saveAll(todasLasRegiones);
    }
}
