package com.example.ordermicroservice.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDishDto {
    @NotNull
    Integer dishId;
    @NotNull
    Integer quantity;
    @NotNull
    Double price;
}
