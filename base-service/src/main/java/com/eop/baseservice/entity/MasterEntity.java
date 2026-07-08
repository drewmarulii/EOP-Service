package com.eop.baseservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MasterEntity extends BaseEntity {

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "is_active")
    private Boolean isActive;

}
