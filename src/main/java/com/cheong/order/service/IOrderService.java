package com.cheong.order.service;

import com.cheong.domain.order.dto.OrderDTO;
import reactor.core.publisher.Mono;

public interface IOrderService {

    Mono<Void> placeOrder(OrderDTO orderDTO);
}
