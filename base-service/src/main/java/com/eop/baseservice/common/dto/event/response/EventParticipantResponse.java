package com.eop.baseservice.common.dto.event.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EventParticipantResponse {

    private String programName;
    private String participantName;

}
