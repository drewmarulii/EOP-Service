package com.eop.baseservice.common.dto.event.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class WorshipTypeResponse {

    private String id;
    private String liturgyGroupId;
    private String liturgyGroupName;
    private String triwulan;
    private String year;
    private String title;
    private String description;
    private LocalDateTime eventStartTime;
    private LocalDateTime eventEndTime;
    private String eventType;
    private Long version;

}
