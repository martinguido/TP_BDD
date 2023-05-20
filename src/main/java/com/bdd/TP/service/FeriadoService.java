package com.bdd.TP.service;

import com.bdd.TP.dao.Feriado;
import com.bdd.TP.repository.FeriadoRepository;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class FeriadoService {
    private final FeriadoRepository feriadoRepository;
    public FeriadoService(FeriadoRepository feriadoRepository){
        this.feriadoRepository = feriadoRepository;
    };

    public boolean loadFeriados(List<Feriado> listaFeriados){
        feriadoRepository.saveAll(listaFeriados);
        return true;
    }
    public Feriado findFirstByFechaIsGreaterThanAndEsFeriadoIsTrue(Date fecha){return feriadoRepository.findFirstByFechaIsGreaterThanAndEsFeriadoIsTrue(fecha);}
    public Feriado feriadoCercano(Date fecha){
        Feriado primerFeriadoEncontrado = findFirstByFechaIsGreaterThanAndEsFeriadoIsTrue(fecha);
        return primerFeriadoEncontrado;
    }
}