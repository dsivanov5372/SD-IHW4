package com.example.ordermicroservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ordermicroservice.exception.DishNotFoundException;
import com.example.ordermicroservice.exception.OrderNotFoundException;
import com.example.ordermicroservice.model.Dish;
import com.example.ordermicroservice.model.Order;
import com.example.ordermicroservice.model.OrderDish;
import com.example.ordermicroservice.model.OrderDishDto;
import com.example.ordermicroservice.model.OrderDto;
import com.example.ordermicroservice.model.Status;
import com.example.ordermicroservice.repository.DishRepository;
import com.example.ordermicroservice.repository.OrderDishRepository;
import com.example.ordermicroservice.repository.OrderRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderService {
    private final DishRepository dishRepository;
    private final OrderRepository orderRepository;
    private final OrderDishRepository dishOrderRepository;

    public Order makeOrder(OrderDto orderDto) {
        List<Dish> list = new ArrayList<>();
        List<OrderDish> orderDishes = new ArrayList<>();
        for (OrderDishDto orderDish : orderDto.getDishes()) {
            Dish dish = dishRepository.findById(orderDish.getDishId())
                                      .orElseThrow(() -> new DishNotFoundException("Такого блюда нет в меню!"));
            list.add(dish);
            orderDishes.add(OrderDish.builder()
                                     .dishId(dish.getId())
                                     .quantity(orderDish.getQuantity())
                                     .price(orderDish.getPrice())
                                     .build());
        }

        Order order = Order.builder()
                           .userId(orderDto.getUserId())
                           .status(Status.WAITING)
                           .specialRequests(orderDto.getSpecialRequests())
                           .dishes(list)
                           .build();
        order = orderRepository.save(order);
   
        for (OrderDish orderDish : orderDishes) {
            orderDish.setDishId(order.getId());
        }
        dishOrderRepository.saveAll(orderDishes);

        return order;
    }

    public Order getOrder(Integer orderId) {
        return orderRepository.findById(orderId)
                              .orElseThrow(() -> new OrderNotFoundException("Заказ не найден!"));
    }
    
}
