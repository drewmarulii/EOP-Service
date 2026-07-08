package com.eop.baseservice.common.dto.liturgy.request;

import com.eop.baseservice.common.dto.BaseUpdateDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateLiturgyGroupRequest extends BaseUpdateDto {

    private String code;
    private String name;
    private String description;

}
