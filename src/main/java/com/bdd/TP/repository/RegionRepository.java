package com.bdd.TP.repository;

import com.bdd.TP.dao.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends CrudRepository<Region, Long> {
//    void deleteByIdRge(Integer idRge);

     Region getByIdRge(Integer id);

     void deleteAllInBatch();
}