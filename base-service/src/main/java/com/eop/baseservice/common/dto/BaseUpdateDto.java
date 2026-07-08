package com.eop.baseservice.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class BaseUpdateDto {

    @NotBlank
    private String id;

    @NotNull
    private Long version;

}
