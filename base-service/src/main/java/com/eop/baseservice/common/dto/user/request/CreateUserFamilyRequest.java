package com.eop.baseservice.common.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserFamilyRequest {

    private String leaderUserId;
    private List<CreateUserFamilyRelationRequest> userFamilyRelationRequests;

}
