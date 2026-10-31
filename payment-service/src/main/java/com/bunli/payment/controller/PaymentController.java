package com.bunli.payment.controller;

import com.bunli.common.exception.ResponseErrorTemplate;
import com.bunli.payment.dto.PaymentRequest;
import com.bunli.payment.entity.Payment;
import com.bunli.payment.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
@Slf4j
public class PaymentController {


    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/pay")
    public ResponseEntity<ResponseErrorTemplate> pay(@RequestBody PaymentRequest paymentRequest) {
        log.info("Payment request: {}", paymentRequest);

        return new ResponseEntity<>(paymentService.processPayment(paymentRequest), HttpStatus.OK);
    }
}
