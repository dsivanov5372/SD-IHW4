package com.example.ordermicroservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ordermicroservice.model.OrderDish;

public interface OrderDishRepository extends JpaRepository<OrderDish, Integer> {
    List<OrderDish> findAllByOrderId(Integer orderId);
}
