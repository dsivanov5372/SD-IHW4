package com.example.ordermicroservice.model;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    @NotNull
    Integer userId;
    String specialRequests;
    List<OrderDishDto> dishes;
}
