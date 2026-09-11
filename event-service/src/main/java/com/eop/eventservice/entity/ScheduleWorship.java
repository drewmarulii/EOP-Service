package com.eop.eventservice.entity;

import com.eop.baseservice.entity.BaseEntity;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "schedule_worships")
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE schedule_worships SET deleted_at = CURRENT_TIMESTAMP WHERE id = ? AND version = ?")
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(name = "json", typeClass = JsonType.class)
public class ScheduleWorship extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worship_type_id", referencedColumnName = "id", nullable = false)
    private WorshipType worshipType;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Type(type = "json")
    @Column(name = "worthip_liturgy", columnDefinition = "jsonb")
    private Map<String, Map<String, String>> worshipLiturgy;

    @Column(name = "location", nullable = false)
    private String location;

}
