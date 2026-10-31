package com.bunli.apigateway.service.impl;

import com.bunli.apigateway.constant.ApiGatewayConstant;
import com.bunli.apigateway.dto.RouteApiRequest;
import com.bunli.apigateway.dto.RouteApiResponse;
import com.bunli.apigateway.entity.ApiRoute;
import com.bunli.apigateway.exception.RouteCreateException;
import com.bunli.apigateway.exception.RouteNotFoundException;
import com.bunli.apigateway.repository.ApiRouteRepository;
import com.bunli.apigateway.service.ApiRouteService;
import com.bunli.apigateway.service.GatewayRouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static com.bunli.apigateway.constant.ApiGatewayErrorCodeConstant.INTERNAL_SERVER_ERROR;
import static com.bunli.apigateway.constant.ApiGatewayErrorCodeConstant.NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiRouteServiceImpl implements ApiRouteService {

    private final ApiRouteRepository apiRouteRepository;
    private final GatewayRouteService gatewayRouteService;

    @Override
    public Mono<RouteApiResponse> create(RouteApiRequest request) {

        ApiRoute apiRoute = mapToApiRoute(request);
        log.info("Create api route: {}", apiRoute);


        return apiRouteRepository.save(apiRoute)
                .doOnSuccess(newRoute -> gatewayRouteService.refreshRoutes())
                .map(this::mapToApiRouteResponse)
                .onErrorMap( e -> {
                    log.error("Error creating route: {}", e.getMessage());
                    return new RouteCreateException(INTERNAL_SERVER_ERROR,
                            "Failed to create route: " + e.getLocalizedMessage());
                });
    }

    @Override
    public Mono<RouteApiResponse> update(Long id, RouteApiRequest request) {
        return apiRouteRepository.update(id,
                request.uri(),
                request.path(),
                request.method(),
                request.description(),
                request.groupCode(),
                request.rateLimit(),
                request.rateLimitDuration(),
                request.status(),
                ApiGatewayConstant.SYSTEM)
                .switchIfEmpty(Mono.error(
                        new RouteNotFoundException(NOT_FOUND,
                                "Route not found with id: " + id)))
                .doOnSuccess(updateRoute -> gatewayRouteService.refreshRoutes())
                .map(this::mapToApiRouteResponse);

    }

    @Override
    public Mono<RouteApiResponse> findById(Long id) {
        return apiRouteRepository.findFirstById(id)
                .switchIfEmpty(Mono.error(new RouteNotFoundException(NOT_FOUND, "Route not found with id: " + id)))
                .map(this::mapToApiRouteResponse)
                .onErrorResume(e -> {
                    log.info("Error finding route by id: {}", e.getMessage());
                    throw new RouteNotFoundException(INTERNAL_SERVER_ERROR, "Internal server error while finding route");
                });
    }

    @Override
    public Flux<RouteApiResponse> findAll() {
        return apiRouteRepository.findAll()
                .map(this::mapToApiRouteResponse)
                .onErrorResume(e -> {
                    log.info("Error finding routes: {}", e.getMessage());
                    throw new RouteNotFoundException(NOT_FOUND, "Route not found");
                });
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return apiRouteRepository.findFirstById(id)
                .switchIfEmpty(Mono.error(new RouteNotFoundException(NOT_FOUND, "Route not found with id: " + id)))
                .flatMap(route -> apiRouteRepository.deleteById(id))
                .then(Mono.fromRunnable(gatewayRouteService::refreshRoutes))
                .onErrorResume(e -> {
                    log.error("Error deleting route by id: {}", e.getMessage());
                    return Mono.error(new RouteNotFoundException(INTERNAL_SERVER_ERROR, "Internal server error while deleting route"));
                }).then();
    }

    @Override
    public Mono<Void> deleteAll() {
        return null;
    }


    public ApiRoute mapToApiRoute(RouteApiRequest request) {
        return ApiRoute.builder()
                .id(request.id())
                .uri(request.uri())
                .path(request.path())
                .method(request.method())
                .description(request.description())
                .groupCode(request.groupCode())
                .rateLimit(request.rateLimit())
                .rateLimitDuration(request.rateLimitDuration())
                .status(request.status())
                .createdBy(ApiGatewayConstant.SYSTEM)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public RouteApiResponse mapToApiRouteResponse(ApiRoute apiRoute) {
        return new RouteApiResponse(
                apiRoute.getId(),
                apiRoute.getUri(),
                apiRoute.getPath(),
                apiRoute.getMethod(),
                apiRoute.getDescription(),
                apiRoute.getGroupCode(),
                apiRoute.getRateLimit(),
                apiRoute.getRateLimitDuration(),
                apiRoute.getStatus(),
                apiRoute.getCreatedAt().toString(),
                apiRoute.getCreatedBy(),
                apiRoute.getUpdatedAt() != null ? apiRoute.getUpdatedAt().toString() : null,
                apiRoute.getUpdatedBy());
    }

}
