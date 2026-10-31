package com.bunli.event.service;

import com.bunli.common.exception.ResponseErrorTemplate;
import com.bunli.event.dto.EventRequest;

public interface EventService {

    ResponseErrorTemplate create(EventRequest request);
    ResponseErrorTemplate update(Long id, EventRequest request);
    ResponseErrorTemplate getById(Long id);
    void delete(Long id);
}
