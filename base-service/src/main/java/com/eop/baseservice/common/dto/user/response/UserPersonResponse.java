package com.eop.baseservice.common.dto.user.response;

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
    private String nik;
    private String fullName;
    private String address;
    private String mobilePhone;
    private String email;
    private String placeOfBirth;
    private LocalDate dateOfBirth;
    private String maritalStatus;
    private String parentId;
    private String parentUid;
    private String parentProfileId;
    private String parentFullName;
    private Long version;

}
