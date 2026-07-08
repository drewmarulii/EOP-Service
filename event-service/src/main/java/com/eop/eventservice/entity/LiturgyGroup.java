package com.eop.eventservice.entity;

import com.eop.baseservice.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "cor_liturgy_groups")
@Where(clause = "deleted_at IS NULL")
@NoArgsConstructor
@AllArgsConstructor
public class LiturgyGroup extends BaseEntity {

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

}
