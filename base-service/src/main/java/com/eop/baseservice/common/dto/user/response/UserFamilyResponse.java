package com.eop.baseservice.common.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserFamilyResponse {

    private UserPersonResponse userFamilyLeader;
    private List<UserFamilyMemberResponse> userFamilyMemberResponses;

}
