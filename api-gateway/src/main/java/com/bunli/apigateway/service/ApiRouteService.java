package com.bunli.apigateway.service;

import com.bunli.apigateway.dto.RouteApiRequest;
import com.bunli.apigateway.dto.RouteApiResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ApiRouteService {

    Mono<RouteApiResponse> create(RouteApiRequest request);
    Mono<RouteApiResponse> update(Long id, RouteApiRequest request);
    Mono<RouteApiResponse> findById(Long id);
    Flux<RouteApiResponse> findAll();
    Mono<Void> deleteById(Long id);
    Mono<Void> deleteAll();

}
