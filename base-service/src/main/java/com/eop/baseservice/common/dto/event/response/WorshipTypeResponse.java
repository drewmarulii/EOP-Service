package com.eop.baseservice.common.dto.event.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class WorshipTypeResponse {

    private String id;
    private String code;
    private String name;
    private String description;
    private String worship;
    private Map<String, String> liturgyTemplate;
    private Long version;
    private Boolean isActive;

}
