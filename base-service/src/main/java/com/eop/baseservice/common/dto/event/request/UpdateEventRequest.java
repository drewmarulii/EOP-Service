package com.eop.baseservice.common.dto.event.request;

import com.eop.baseservice.common.dto.BaseUpdateDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class UpdateEventRequest extends BaseUpdateDto {

    private String liturgyGroupId;
    private Integer triwulan;
    private Integer year;
    private String title;
    private String description;
    private String eventStartTime;
    private String eventEndTime;
    private String eventType;
    private Map<String, Object> participantData;

}
