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
    //List<Medicion> findByFechaAndRegion(Date fecha, Optional<Region> region);

    List<Medicion> findByFecha(Date fecha);

    Optional<Medicion> findByFechaAndRegion(Date date,Optional<Region> region);
    @Query(value = "SELECT avg(demanda) from mediciones where fecha <> ?1", nativeQuery = true)
    double findSomeDateAvgDemand(Date fecha);

    void deleteAllInBatch();

    Medicion getByFechaAndRegion(Date date,Optional<Region> region);

    @Query(value = "SELECT my_id, id_region, fecha, demanda, temperatura FROM mediciones WHERE (id_region, demanda) IN (SELECT id_region, MAX(demanda) FROM mediciones GROUP BY id_region) ORDER BY id_region", nativeQuery = true)
    List<Medicion> dateWithMaxDemandByRegion();


}