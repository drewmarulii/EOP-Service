package com.eop.userservice.entity;

import com.eop.baseservice.common.constant.UserRole;
import com.eop.baseservice.common.constant.UserStatus;
import com.eop.baseservice.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "cor_users")
@Where(clause = "deleted_at IS NULL")
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Column(name = "uid", nullable = false)
    private String uid;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private UserStatus status;

    @Column(name = "last_login_info")
    private ZonedDateTime lastLoginInfo;

}
