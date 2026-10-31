package com.bunli.payment.service;

import com.bunli.common.exception.ResponseErrorTemplate;
import com.bunli.payment.dto.PaymentRequest;

public interface PaymentService {    // get by transaction id
    // get by payment id

    // refund payment

    ResponseErrorTemplate processPayment(PaymentRequest paymentRequest);


}
