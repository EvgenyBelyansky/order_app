package ru.pet_project.order_app.mapper;

import org.springframework.stereotype.Component;
import ru.pet_project.order_app.dto.OrderInputDto;
import ru.pet_project.order_app.dto.OrderOutputDto;
import ru.pet_project.order_app.dto.OrderShortOutputDto;
import ru.pet_project.order_app.entity.OrderEntity;
import ru.pet_project.order_app.entity.UserEntity;

@Component
public class OrderMapper {

    public OrderOutputDto fromEntityToOutputDto(OrderEntity entity) {
        return OrderOutputDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .userOrderNumber(entity.getUserOrderNumber())
                .description(entity.getDescription())
                .createdDate(entity.getCreatedDate())
                .updatedDate(entity.getUpdatedDate())
                .status(entity.getStatus())
                .build();
    }

    public OrderShortOutputDto fromEntityToShortOutputDto(OrderEntity entity) {
        return OrderShortOutputDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .userOrderNumber(entity.getUserOrderNumber())
                .status(entity.getStatus())
                .build();
    }

    public OrderEntity fromInputDtoToEntity(OrderInputDto dto, Integer userOrderNumber, UserEntity user) {
        return OrderEntity.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .userOrderNumber(userOrderNumber)
                .user(user)
                .build();
    }
}
