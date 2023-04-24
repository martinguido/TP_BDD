package com.bdd.TP.repository;

import com.bdd.TP.dao.Feriado;
import com.bdd.TP.dao.Medicion;
import com.bdd.TP.dao.Region;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface FeriadoRepository extends CrudRepository<Feriado, Date> {

    Feriado findFirstByFechaIsGreaterThanAndEsFeriadoIsTrue(Date fecha);
}