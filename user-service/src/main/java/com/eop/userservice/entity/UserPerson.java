package com.eop.userservice.entity;

import com.eop.baseservice.common.constant.Gender;
import com.eop.baseservice.common.constant.MaritalStatus;
import com.eop.baseservice.common.constant.UserStatus;
import com.eop.baseservice.entity.BaseEntity;
import com.eop.baseservice.entity.MasterEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "user_persons")
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE user_persons SET deleted_at = CURRENT_TIMESTAMP WHERE id = ? AND version = ?")
@NoArgsConstructor
@AllArgsConstructor
public class UserPerson extends MasterEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "place_of_birth", nullable = false)
    private String placeOfBirth;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "address", nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status", nullable = false)
    private MaritalStatus maritalStatus;

    @Column(name = "married_date")
    private LocalDate marriedDate;

    @Column(name = "mobile_phone", nullable = false)
    private String mobilePhone;

    @Column(name = "email")
    private String email;

    @Column(name = "is_family_leader", nullable = false)
    private Boolean isFamilyLeader;

    @Column(name = "is_passed_away", nullable = false)
    private Boolean isPassedAway;

    @Column(name = "passed_away_date")
    private LocalDate passedAwayDate;

    @Column(name = "profile_picture")
    private String profilePicture;

}
