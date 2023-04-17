package com.bdd.TP.repository;

import com.bdd.TP.dao.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
//    void deleteById_region(Integer idRegion);
//
     Region getByIdRge(Integer id);
}