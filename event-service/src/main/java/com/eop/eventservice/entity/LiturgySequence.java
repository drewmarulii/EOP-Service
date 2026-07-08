package com.eop.eventservice.entity;

import com.eop.baseservice.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "cor_liturgy_sequences")
@Where(clause = "deleted_at IS NULL")
@NoArgsConstructor
@AllArgsConstructor
public class LiturgySequence extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liturgy_group_id", referencedColumnName = "id", nullable = false)
    private LiturgyGroup liturgyGroup;

    @Column(name = "sequence_number", nullable = false)
    private Integer sequenceNumber;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

}
