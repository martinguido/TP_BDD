package com.bdd.TP.service;

import com.bdd.TP.dao.Region;
import com.bdd.TP.dto.RegionDTO;
import com.bdd.TP.repository.RegionRepository;
import org.springframework.stereotype.Service;

@Service
public class RegionService {

    private final RegionRepository regionRepository;

    public RegionService(RegionRepository regionRepository){
        this.regionRepository = regionRepository;
    };
    public RegionDTO createRegion(RegionDTO regionDTO){
        Region aNewRegion = new Region(regionDTO.getId_region(), regionDTO.getNombre());
        regionRepository.save(aNewRegion);
//        System.out.println(regionRepository.findAll().toString());
        return regionDTO;
    }
    public void deleteRegion(Region region){
        regionRepository.delete(region);
    }

    public Region getElementByIdRegion(Integer id_region){
        return  regionRepository.getByIdRegion(id_region);
    }
}