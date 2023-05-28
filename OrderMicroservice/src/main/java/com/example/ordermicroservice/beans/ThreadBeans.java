package com.example.ordermicroservice.beans;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.example.ordermicroservice.model.Dish;
import com.example.ordermicroservice.model.Order;
import com.example.ordermicroservice.model.OrderDish;
import com.example.ordermicroservice.model.Status;
import com.example.ordermicroservice.repository.DishRepository;
import com.example.ordermicroservice.repository.OrderDishRepository;
import com.example.ordermicroservice.repository.OrderRepository;

@Configuration
public class ThreadBeans {
    
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Thread dishTableChecker(OrderRepository orderRepository,
                                   DishRepository dishRepository,
                                   OrderDishRepository orderDishRepository) {
        Thread thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                List<Order> orders = orderRepository.findAllOrdersNeedToBeDone();
                for (Order order : orders) {
                    if (order.getStatus().equals(Status.IN_PROGRESS)) {
                        order.setStatus(Status.DONE);
                    } else {
                        List<OrderDish> orderDishes = orderDishRepository.findAllByOrderId(order.getId());
                        List<Dish> dishes = new ArrayList<>();
                        for (OrderDish orderDish : orderDishes) {
                            Dish dish = dishRepository.findById(orderDish.getDishId()).get();
                            if (dish.getQuantity() >= orderDish.getQuantity()) {
                                dishes.add(dish);
                            }
                        }
    
                        if (dishes.size() == orderDishes.size()) {
                            for (int i = 0; i < dishes.size(); ++i) {
                                Dish dish = dishes.get(i);
                                Integer currQuantity = dish.getQuantity();
                                dish.setQuantity(currQuantity - orderDishes.get(i).getQuantity());
                            }
                            dishRepository.saveAll(dishes);
                            order.setStatus(Status.IN_PROGRESS);
                        } else {
                            order.setStatus(Status.CANCELED);
                        }
                    }
                    orderRepository.save(order);
                }

                try {
                    Thread.sleep(Duration.ofMinutes(5).toMillis());
                } catch (Exception e) {
                    return;
                }
            }
        });
        thread.start();
        return thread;
    }
}
