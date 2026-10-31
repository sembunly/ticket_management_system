package com.bunli.apigateway.service;

import com.bunli.apigateway.entity.RequestLog;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface RequestLogService {

    Mono<RequestLog> save(RequestLog requestLog);

}
