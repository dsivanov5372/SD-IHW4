package com.example.ordermicroservice.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DishDto {
    @NotBlank
    String name;
    @NotBlank
    String description;
    @NotNull
    @DecimalMin(value = "0.0")
    Double price;
    @NotNull
    @Min(0)
    Integer quantity;
}
