package com.eop.baseservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public class MasterEntity extends BaseEntity {

    @Column(name = "is_active", columnDefinition = "boolean default true")
    private Boolean isActive = true;

}
