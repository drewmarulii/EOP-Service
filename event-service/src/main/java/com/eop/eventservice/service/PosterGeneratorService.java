package com.eop.eventservice.service;

import com.eop.eventservice.entity.Event;

import java.io.IOException;

public interface PosterGeneratorService {

    String generate(Event event) throws IOException;

}
