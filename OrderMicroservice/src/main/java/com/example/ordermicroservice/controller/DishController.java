package com.example.ordermicroservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ordermicroservice.model.Dish;
import com.example.ordermicroservice.model.DishDto;
import com.example.ordermicroservice.service.DishService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class DishController {
    private final DishService service;

    @GetMapping("/menu")
    public List<Dish> getMenu() {
        return service.getMenu();
    }

    @PostMapping("/dish")
    public Dish addDish(@Valid DishDto dishDto) {
        return service.addDish(dishDto);
    }

    @PatchMapping("/dish/{id}")
    public Dish updateDish(@Valid DishDto dishDto, @PathVariable("id") Integer dishId) {
        return service.updateDish(dishDto, dishId);
    }

    @DeleteMapping("/dish/{id}")
    public void deleteDish(@PathVariable("id") Integer dishId) {
        service.deleteDish(dishId);
    }
}
