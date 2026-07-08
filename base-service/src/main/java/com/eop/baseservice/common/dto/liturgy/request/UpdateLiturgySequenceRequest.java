package com.eop.baseservice.common.dto.liturgy.request;

import com.eop.baseservice.common.dto.BaseUpdateDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateLiturgySequenceRequest extends BaseUpdateDto {

    private Integer sequenceNumber;
    private String title;
    private String description;

}
