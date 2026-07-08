package com.eop.eventservice.entity;

import com.eop.baseservice.common.converter.JsonToMapConverter;
import com.eop.baseservice.entity.BaseEntity;
import com.eop.eventservice.common.constant.EventType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
@TypeDef(
    name = "jsonb",
    typeClass = JsonBinaryType.class
)
@Entity
@Table(name = "cor_events")
@Where(clause = "deleted_at IS NULL")
@NoArgsConstructor
@AllArgsConstructor
public class Event extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liturgy_group_id", referencedColumnName = "id", nullable = false)
    private LiturgyGroup liturgyGroup;

    @Column(name = "triwulan", nullable = false)
    private Integer triwulan;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "event_start_time", nullable = false)
    private LocalDateTime eventStartTime;

    @Column(name = "event_end_time")
    private LocalDateTime eventEndTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    private EventType eventType;

    @Type(type = "jsonb")
    @Column(name = "participant_data", columnDefinition = "jsonb")
    private Map<String, Object> participantData;

}
