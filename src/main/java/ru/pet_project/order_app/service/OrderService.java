package ru.pet_project.order_app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pet_project.order_app.dto.OrderInputDto;
import ru.pet_project.order_app.dto.OrderOutputDto;
import ru.pet_project.order_app.dto.OrderShortOutputDto;
import ru.pet_project.order_app.entity.OrderEntity;
import ru.pet_project.order_app.entity.UserEntity;
import ru.pet_project.order_app.mapper.OrderMapper;
import ru.pet_project.order_app.repository.OrderRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final UserService userService;

    @Transactional
    public void createNewOrder(OrderInputDto inputDto, UUID userId) {
        if (inputDto == null) {
            throw new RuntimeException("Передан DTO с NULL значением!");
        }

        UserEntity user = userService.getUserById(userId);

        Integer orderNumber = repository
                .findMaxUserOrderNumber(userId)
                .orElse(0) + 1;

        OrderEntity order = mapper.fromInputDtoToEntity(inputDto, orderNumber, user);

        repository.save(order);
    }

    @Transactional
    public void cancelOrderById(UUID id) {
        if (id == null) {
            throw new RuntimeException("Передан ID c [NULL] значением");
        }

        OrderEntity entity = repository.findById(id)
                .orElseThrow(() -> new  RuntimeException("Заказ с ID [%s] не найден".formatted(id)));

        entity.markAsCanceled();
        repository.save(entity);
    }

    @Transactional
    public void markAsProcessingById(UUID id) {
        if (id == null) {
            throw new RuntimeException("Передан ID c [NULL] значением");
        }

        OrderEntity entity = repository.findById(id)
                .orElseThrow(() -> new  RuntimeException("Заказ с ID [%s] не найден".formatted(id)));
        entity.markAsProcessing();
        repository.save(entity);
    }

    @Transactional
    public void markAsShippedById(UUID id) {
        if (id == null) {
            throw new RuntimeException("Передан ID c [NULL] значением");
        }

        OrderEntity entity = repository.findById(id)
                .orElseThrow(() -> new  RuntimeException("Заказ с ID [%s] не найден".formatted(id)));
        entity.markAsShipped();
        repository.save(entity);
    }

    @Transactional
    public void markAsExecutedById(UUID id) {
        if (id == null) {
            throw new RuntimeException("Передан ID c [NULL] значением");
        }

        OrderEntity entity = repository.findById(id)
                .orElseThrow(() -> new  RuntimeException("Заказ с ID [%s] не найден".formatted(id)));
        entity.markAsExecuted();
        repository.save(entity);
    }

    @Transactional
    public OrderOutputDto getOrderById(UUID id) {
        if (id == null) {
            throw new RuntimeException("Передан ID c [NULL] значением");
        }

        OrderEntity entity = repository.findById(id)
                .orElseThrow(() -> new  RuntimeException("Заказ с ID [%s] не найден".formatted(id)));

        return mapper.fromEntityToOutputDto(entity);
    }

    @Transactional
    public OrderOutputDto getOrderByUserOrderNumber(UUID userId, int number) {
        if (number <= 0) {
            throw new RuntimeException("Передан номер заказа с отрицательным или нулевым значением!");
        }

        final UserEntity user = userService.getUserById(userId);

        OrderEntity entity = repository.findByUserOrderNumberAndUserId(number, userId)
                .orElseThrow(() -> new  RuntimeException(
                        "У пользователя [%s] Заказ №[%s] не найден".formatted(user.getName(), number))
                );

        return mapper.fromEntityToOutputDto(entity);
    }


    @Transactional
    public List<OrderShortOutputDto> getAllOrdersByUserId(UUID userId) {
        final UserEntity user = userService.getUserById(userId);

        return repository.findAllByUserId(user.getId()).stream()
                .map(mapper::fromEntityToShortOutputDto)
                .toList();
    }




}
