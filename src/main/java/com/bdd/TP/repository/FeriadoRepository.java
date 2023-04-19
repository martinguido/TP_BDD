package com.bdd.TP.repository;

import com.bdd.TP.dao.Feriado;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;

@Repository
public interface FeriadoRepository extends CrudRepository<Feriado, Date> {
}