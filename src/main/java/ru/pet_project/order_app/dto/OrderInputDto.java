package ru.pet_project.order_app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderInputDto {

    @NotBlank
    private String name;

    @NotBlank
    private String description;
}
