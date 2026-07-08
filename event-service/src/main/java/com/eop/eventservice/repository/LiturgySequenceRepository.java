package com.eop.eventservice.repository;

import com.eop.eventservice.entity.LiturgySequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LiturgySequenceRepository extends JpaRepository<LiturgySequence, String> {

    List<LiturgySequence> findAllByLiturgyGroupId(@Param(value = "liturgyGroupId") String liturgyGroupId);

}
