package com.bdd.TP.service;

import com.bdd.TP.dao.Feriado;
import com.bdd.TP.dto.FeriadoDTO;
import com.bdd.TP.repository.FeriadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeriadoService {

    private final FeriadoRepository feriadoRepository;

    public FeriadoService(FeriadoRepository feriadoRepository){
        this.feriadoRepository = feriadoRepository;
    };
    public FeriadoDTO createFeriado(FeriadoDTO feriadoDTO){
        Feriado aNewFeriado = new Feriado(feriadoDTO.getFecha(), feriadoDTO.isEsFeriado());
        feriadoRepository.save(aNewFeriado);
        System.out.println(feriadoRepository.findAll().toString());
        return feriadoDTO;
    }

    public boolean loadFeriados(List<Feriado> listaFeriados){
        feriadoRepository.saveAll(listaFeriados);
        return true;
    }
}