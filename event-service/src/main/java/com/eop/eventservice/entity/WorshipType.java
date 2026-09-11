package com.eop.eventservice.entity;

import com.eop.baseservice.common.constant.UserRole;
import com.eop.baseservice.common.constant.Worship;
import com.eop.baseservice.entity.MasterEntity;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "worship_types")
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE worship_types SET deleted_at = CURRENT_TIMESTAMP WHERE id = ? AND version = ?")
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(name = "json", typeClass = JsonType.class)
public class WorshipType extends MasterEntity {

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "worship_type", nullable = false)
    private Worship worship;

    @Type(type = "json")
    @Column(name = "liturgy_template", columnDefinition = "jsonb")
    private Map<String, String> liturgyTemplate;

}
