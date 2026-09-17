package com.eop.baseservice.common.dto.user.request;

import com.eop.baseservice.common.constant.FamilyRelationship;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserFamilyRequest {

    private String id;
    private FamilyRelationship relationship;
    private Long version;

}
