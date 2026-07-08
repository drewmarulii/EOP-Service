package com.eop.eventservice.repository;

import com.eop.eventservice.entity.LiturgyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiturgyGroupRepository extends JpaRepository<LiturgyGroup, String> {

    boolean existsByCode(String code);

}
