package ru.pet_project.order_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.pet_project.order_app.enums.OrderStatus;

import java.util.UUID;

@Builder
@AllArgsConstructor
@Getter
@ToString
public class OrderShortOutputDto {

    private UUID id;

    private String name;

    private Integer userOrderNumber;

    private OrderStatus status;
}
