package com.eop.baseservice.common.dto.user.response;

import com.eop.baseservice.common.constant.Gender;
import com.eop.baseservice.common.constant.MaritalStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserPersonResponse {

    private String id;
    private String fullName;
    private String placeOfBirth;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String address;
    private MaritalStatus maritalStatus;
    private LocalDate marriedDate;
    private String mobilePhone;
    private String email;
    private Boolean isFamilyLeader;
    private Boolean isPassedAway;
    private LocalDate passedAwayDate;
    private String profilePicture;

    private UserResponse userResponse;

}
