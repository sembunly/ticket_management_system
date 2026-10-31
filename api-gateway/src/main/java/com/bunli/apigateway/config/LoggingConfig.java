package com.bunli.apigateway.config;

import com.bunli.apigateway.logging.GatewayLoggingProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;

@Slf4j
@Configuration
@EnableWebFlux
@EnableConfigurationProperties(GatewayLoggingProperties.class)
public class LoggingConfig {

}
