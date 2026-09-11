package com.eop.baseservice.common.dto.event.request;

import com.eop.baseservice.common.constant.Worship;
import com.eop.baseservice.common.dto.BaseUpdateDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class UpdateWorshipTypeRequest extends BaseUpdateDto {

    private String name;
    private Worship worship;
    private List<String> liturgyTemplate;
    private Boolean isActive;

}
