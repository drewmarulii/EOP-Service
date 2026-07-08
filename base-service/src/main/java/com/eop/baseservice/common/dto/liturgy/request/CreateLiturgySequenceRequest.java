package com.eop.baseservice.common.dto.liturgy.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateLiturgySequenceRequest {

    private Integer sequenceNumber;
    private String title;
    private String description;

}
