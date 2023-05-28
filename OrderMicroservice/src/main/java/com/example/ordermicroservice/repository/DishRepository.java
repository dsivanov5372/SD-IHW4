package com.example.ordermicroservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.ordermicroservice.model.Dish;

public interface DishRepository extends JpaRepository<Dish, Integer> {
    @Query(value = "SELECT * FROM order_dish WHERE quantity != 0", nativeQuery = true)
    List<Dish> findAllDishes();
}
