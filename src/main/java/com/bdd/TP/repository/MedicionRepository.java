package com.bdd.TP.repository;

import com.bdd.TP.dao.Medicion;
import com.bdd.TP.dao.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface MedicionRepository extends JpaRepository<Medicion, Long> {
    Optional<Medicion> findByFechaAndRegion(Date date,Optional<Region> region);

    @Query(value = "SELECT avg(demanda) from mediciones where fecha <> ?1", nativeQuery = true)
    double findSomeDateAvgDemand(Date fecha);

    void deleteAllInBatch();

    @Query(value = "SELECT DISTINCT ON (id_region) id_region, fecha, demanda FROM (SELECT id_region, fecha, AVG(demanda) AS demanda FROM mediciones GROUP BY id_region, fecha) AS subquery ORDER BY id_region, demanda DESC ", nativeQuery = true)
    List<Object[]> dateWithMaxDemandByRegion();
}