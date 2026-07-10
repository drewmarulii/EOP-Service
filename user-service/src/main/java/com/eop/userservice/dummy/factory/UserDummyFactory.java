package com.eop.userservice.dummy.factory;

import com.eop.baseservice.common.constant.MaritalStatus;
import com.eop.baseservice.common.constant.UserRole;
import com.eop.baseservice.common.constant.UserStatus;
import com.eop.baseservice.entity.BaseEntity;
import com.eop.userservice.entity.User;
import com.eop.userservice.entity.UserPerson;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Component
@AllArgsConstructor
public class UserDummyFactory {

    private final PasswordEncoder passwordEncoder;

    public User create(String uid, UserRole role, UserStatus status) {
        User user = new User();
        user.setUid(uid);
        user.setPassword(
            passwordEncoder.encode("password123")
        );
        user.setRole(role);
        user.setStatus(status);
        return setAudit(user);
    }

    public UserPerson createPerson(User user, String nik, String fullName, MaritalStatus maritalStatus, User parent) {
        UserPerson userPerson = new UserPerson();
        userPerson.setUser(user);
        userPerson.setNik(nik);
        userPerson.setFullName(fullName);
        userPerson.setAddress("JL. Tangerang Indonesia 5-7, Tangerang");
        userPerson.setMobilePhone("081234567890");
        userPerson.setEmail(user.getUid() + "@email.com");
        userPerson.setPlaceOfBirth("Tangerang");
        userPerson.setDateOfBirth(LocalDate.of(1982, 5, 10));
        userPerson.setMaritalStatus(maritalStatus);
        userPerson.setParent(parent);
        return setAudit(userPerson);
    }

    public <T extends BaseEntity> T setAudit(T entity) {
        entity.setCreatedBy("SYSTEM");
        entity.setCreatedAt(ZonedDateTime.now());
        entity.setUpdatedBy("SYSTEM");
        entity.setUpdatedAt(ZonedDateTime.now());
        entity.setVersion(0L);
        return entity;
    }
}
