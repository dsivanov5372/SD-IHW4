package com.example.ordermicroservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.ordermicroservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query(value = "SELECT * FROM order WHERE status == 'WAITING' OR status == 'IN_PROGRESS'", nativeQuery = true)
    List<Order> findAllOrdersNeedToBeDone();
}
