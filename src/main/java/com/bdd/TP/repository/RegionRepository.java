package com.bdd.TP.repository;

import com.bdd.TP.dao.Region;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegionRepository extends CrudRepository<Region, Long> {
     Region getByIdRge(Integer id);
     void deleteAllInBatch();
     List<Region> findAll();
     Optional<Region> findByIdRge(Integer idRge);

     Optional<Region> findById(Integer id);
}