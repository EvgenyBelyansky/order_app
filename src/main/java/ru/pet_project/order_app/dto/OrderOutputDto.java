package ru.pet_project.order_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.pet_project.order_app.enums.OrderStatus;

import java.time.Instant;
import java.util.UUID;

@Builder
@AllArgsConstructor
@Getter
@ToString
public class OrderOutputDto {

    private UUID id;

    private String name;

    private Integer userOrderNumber;

    private String description;

    private Instant createdDate;

    private Instant updatedDate;

    private OrderStatus status;
}
