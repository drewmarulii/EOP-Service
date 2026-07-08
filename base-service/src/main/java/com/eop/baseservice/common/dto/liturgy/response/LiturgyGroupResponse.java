package com.eop.baseservice.common.dto.liturgy.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LiturgyGroupResponse {

    private String id;
    private String code;
    private String name;
    private List<LiturgySequenceResponse> liturgySequenceResponses;
    private Long version;

}
