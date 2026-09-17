package com.eop.baseservice.common.dto.event.request;

import com.eop.baseservice.common.dto.BaseUpdateDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class UpdateScheduleWorshipRequest extends BaseUpdateDto {

    private String worshipTypeId;
    private String startTime;
    private String endTime;
    private Map<String, Map<String, String>> worshipLiturgy;
    private String location;

}
