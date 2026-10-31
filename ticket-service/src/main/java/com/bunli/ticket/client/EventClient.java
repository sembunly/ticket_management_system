package com.bunli.ticket.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.bunli.common.exception.ResponseErrorTemplate;

@Configuration
public class EventClient {

    private final WebClient webClient;

    public EventClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @Value("${event.service.url}")
    private String eventServiceUrl;

    public ResponseErrorTemplate getEventById(Long eventId) {
        return webClient.get()
                .uri(eventServiceUrl + "/{id}", eventId)
                .header("Accept", "application/json")
                .retrieve()
                .bodyToMono(ResponseErrorTemplate.class)
                .block();
    }
}
