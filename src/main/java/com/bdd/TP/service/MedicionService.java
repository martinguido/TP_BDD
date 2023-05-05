package com.bdd.TP.service;

import com.bdd.TP.dao.Medicion;
import com.bdd.TP.dao.Region;
import com.bdd.TP.dto.MedicionDTO;
import com.bdd.TP.exceptions.RegionDoesNotExistException;
import com.bdd.TP.repository.MedicionRepository;
import com.bdd.TP.repository.RegionRepository;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class MedicionService {
    private final MedicionRepository medicionRepository;
    private final RegionRepository regionRepository;
    public MedicionService(MedicionRepository medicionRepository, RegionRepository regionRepository) {
        this.medicionRepository = medicionRepository;
        this.regionRepository = regionRepository;
    }
    public Optional<Medicion> findByFechaAndRegion(Date date,Optional<Region> region){return medicionRepository.findByFechaAndRegion(date,region);}
    public Medicion createMedicion(MedicionDTO medicionDTO) {
        Region region = medicionDTO.getRegion();
        Integer idRge = region.getId();
        Region savedRegion = regionRepository.findById(idRge)
                .orElseThrow(
                        ()-> new RegionDoesNotExistException("The region with id: " + idRge + " does not exists")
                );
        medicionDTO.setRegion(savedRegion);
        Medicion aNewMedicion = new Medicion(medicionDTO.getDemanda(),medicionDTO.getTemperatura(), medicionDTO.getFecha(), medicionDTO.getRegion());
        //medicionRepository.save(aNewMedicion);
        return aNewMedicion;
    }

    public HashMap<String, Double> sumarDemandayTemperaturaTotal(List<HashMap<?,?>> mediciones)
    {
//
        HashMap<String , Double> medicionTotal= new HashMap<String, Double>();
        double demanda = 0.0;
        double temperatura = 0.0;
        for (HashMap<?, ?> medicion : mediciones) {
            if ((medicion.get("dem"))!= null){
                demanda = demanda + (Integer) medicion.get("dem");
            }
            demanda = demanda + (Integer) medicion.get("dem");
            if ((medicion.get("temp")) != null) {
                temperatura = temperatura + (Double) medicion.get("temp");
            }
        }
        medicionTotal.put("demanda", demanda);
        medicionTotal.put("temperatura", temperatura);
        return medicionTotal;
    }

    public boolean existeMedicion(Date fecha, Optional<Region> region){
        Optional<Medicion> medicion = medicionRepository.findByFechaAndRegion(fecha, region);
        return medicion.isPresent();
    }


    public List<Medicion> findByFecha(Date fecha){ return medicionRepository.findByFecha(fecha);}

    public double avgDemandaFechaEspecifica(Date fecha){
        double demandaEsteDia = medicionRepository.findSomeDateAvgDemand(fecha);
        return  demandaEsteDia;
    }

    public List<Medicion> dateWithMaxDemandByRegion() {
        return medicionRepository.dateWithMaxDemandByRegion();

    }

    public void saveMediciones(List<Medicion> listaMediciones) {
        medicionRepository.saveAll(listaMediciones);
    }

    public void saveMedicion(Medicion medicion) {
        medicionRepository.save(medicion);
    }

    public void deleteAllMediciones(){medicionRepository.deleteAllInBatch();}
}