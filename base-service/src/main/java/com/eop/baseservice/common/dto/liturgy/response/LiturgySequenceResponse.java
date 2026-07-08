package com.eop.baseservice.common.dto.liturgy.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LiturgySequenceResponse {

    private String id;
    private Integer sequenceNumber;
    private String title;
    private String description;
    private Long version;

}
