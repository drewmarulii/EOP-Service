package com.eop.eventservice.repository;

import com.eop.eventservice.entity.WorshipType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorshipTypeRepository extends JpaRepository<WorshipType, String> {

    boolean existsByCode(String code);

}
