package com.example.ordermicroservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ordermicroservice.exception.DishNotFoundException;
import com.example.ordermicroservice.model.Dish;
import com.example.ordermicroservice.model.DishDto;
import com.example.ordermicroservice.repository.DishRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DishService {
    private final DishRepository dishRepository;

    public List<Dish> getMenu() {
        return dishRepository.findAllDishes();
    }

    public Dish addDish(DishDto dishDto) {
        return dishRepository.save(Dish.builder()
                                       .name(dishDto.getName())
                                       .description(dishDto.getDescription())
                                       .price(dishDto.getPrice())
                                       .quantity(dishDto.getQuantity())
                                       .build());
    }

    public Dish updateDish(DishDto dishDto, Integer dishId) {
        Dish dish = dishRepository.findById(dishId).orElseThrow(() -> new DishNotFoundException("Блюдо не найдено!"));
        dish.setName(dishDto.getName());
        dish.setDescription(dishDto.getDescription());
        dish.setPrice(dishDto.getPrice());
        dish.setQuantity(dishDto.getQuantity());
        return dishRepository.save(dish);
    }

    public void deleteDish(Integer dishId) {
        dishRepository.deleteById(dishId);
    }
}
