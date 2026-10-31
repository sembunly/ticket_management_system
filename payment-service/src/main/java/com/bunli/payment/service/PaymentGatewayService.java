package com.bunli.payment.service;

import com.bunli.payment.dto.PaymentRequest;

public interface PaymentGatewayService {

    boolean processPayment(PaymentRequest paymentRequest);
}
