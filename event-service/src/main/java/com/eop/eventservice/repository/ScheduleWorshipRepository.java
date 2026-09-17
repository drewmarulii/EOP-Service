package com.eop.eventservice.repository;

import com.eop.eventservice.entity.ScheduleWorship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleWorshipRepository extends JpaRepository<ScheduleWorship, String> {

    boolean existsByWorshipTypeIdAndStartTimeAndEndTime(String worshipTypeId, String startTime, String endTime);

}
