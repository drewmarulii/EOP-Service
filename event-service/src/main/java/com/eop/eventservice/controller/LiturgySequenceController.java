package com.eop.eventservice.controller;

import com.eop.eventservice.service.LiturgySequenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/liturgy-sequences")
public class LiturgySequenceController {

    private final LiturgySequenceService liturgySequenceService;

}
