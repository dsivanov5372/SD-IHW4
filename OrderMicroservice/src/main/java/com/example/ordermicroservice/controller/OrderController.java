package com.example.ordermicroservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ordermicroservice.model.Order;
import com.example.ordermicroservice.model.OrderDto;
import com.example.ordermicroservice.service.OrderService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class OrderController {
    private final OrderService service;

    @PostMapping("/order")
    public Order makeOrder(@Valid OrderDto orderDto) {
        return service.makeOrder(orderDto);
    }

    @GetMapping("/order/{id}")
    public Order getOrder(@PathVariable("id") Integer orderId) {
        return service.getOrder(orderId);
    }
}
