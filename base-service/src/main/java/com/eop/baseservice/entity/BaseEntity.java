package com.eop.baseservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @Column(name = "deleted_at")
    private ZonedDateTime deletedAt;

    @Version
    @Column(name = "version")
    private Long version;

    @PrePersist
    public void prePersist() {
        if (createdBy == null || createdBy.isEmpty()) {
            createdBy = "SYSTEM";
        }

        if (createdAt == null) {
            createdAt = ZonedDateTime.now();
        }

        if (updatedBy == null || updatedBy.isEmpty()) {
            updatedBy = "SYSTEM";
        }

        if (updatedAt == null) {
            updatedAt = ZonedDateTime.now();
        }

        if (version == null) {
            version = 0L;
        }
    }
}
