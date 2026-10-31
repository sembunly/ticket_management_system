package com.bunli.apigateway.repository;

import com.bunli.apigateway.entity.RequestLog;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  RequestLogRepository extends ReactiveCrudRepository<RequestLog, Long> {
}
