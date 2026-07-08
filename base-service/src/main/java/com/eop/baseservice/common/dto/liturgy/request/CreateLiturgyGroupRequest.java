package com.eop.baseservice.common.dto.liturgy.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateLiturgyGroupRequest {

    private String code;
    private String name;
    private String description;
    private List<CreateLiturgySequenceRequest> liturgySequenceRequests;

}
