package com.bdd.TP.repository;

import com.bdd.TP.dao.Feriado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface FeriadoRepository extends JpaRepository<Feriado, Date> {
}