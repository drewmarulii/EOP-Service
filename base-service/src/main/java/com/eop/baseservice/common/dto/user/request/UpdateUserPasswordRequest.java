package com.eop.baseservice.common.dto.user.request;

import com.eop.baseservice.common.constant.UserRole;
import com.eop.baseservice.common.dto.BaseUpdateDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserPasswordRequest extends BaseUpdateDto {

    @NotBlank
    private String currPassword;

    @NotBlank
    private String newPassword;

    @NotBlank
    private String confPassword;

}
