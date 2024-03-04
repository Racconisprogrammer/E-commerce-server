package com.example.ecommerce.service.impl;

import com.example.ecommerce.model.OrderItem;
import com.example.ecommerce.repository.OrderItemRepository;
import com.example.ecommerce.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
}
