package com.bunli.order.service;

import com.bunli.common.exception.ResponseErrorTemplate;
import com.bunli.order.dto.OrderRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface OrderService {

    ResponseErrorTemplate createOrder(OrderRequest orderRequest, HttpServletRequest httpServletRequest);
    ResponseErrorTemplate getOrderById(Long orderId);
    ResponseErrorTemplate cancelOrder(Long orderId, HttpServletRequest httpServletRequest);

}
