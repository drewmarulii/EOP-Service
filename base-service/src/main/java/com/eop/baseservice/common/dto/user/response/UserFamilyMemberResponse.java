package com.eop.baseservice.common.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserFamilyMemberResponse {

    private String id;
    private UserPersonResponse userFamilyMember;
    private String relationship;
    private Long version;
    private Boolean isActive;

}
