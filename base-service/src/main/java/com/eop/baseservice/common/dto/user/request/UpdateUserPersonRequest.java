package com.eop.baseservice.common.dto.user.request;

import com.eop.baseservice.common.dto.BaseUpdateDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserPersonRequest extends BaseUpdateDto {

    private String nik;
    private String fullName;
    private String address;
    private String mobilePhone;
    private String email;
    private String placeOfBirth;
    private LocalDate dateOfBirth;
    private String maritalStatus;
    private String parentId;

}
