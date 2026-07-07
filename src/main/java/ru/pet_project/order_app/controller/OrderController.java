package ru.pet_project.order_app.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.pet_project.order_app.dto.OrderInputDto;
import ru.pet_project.order_app.dto.OrderOutputDto;
import ru.pet_project.order_app.dto.OrderShortOutputDto;
import ru.pet_project.order_app.entity.UserEntity;
import ru.pet_project.order_app.service.OrderService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping("/create")
    public ResponseEntity<Void> createOrder(@Valid @RequestBody OrderInputDto request,
                                            @AuthenticationPrincipal UserEntity user) {
        service.createNewOrder(request, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<OrderShortOutputDto>> getUserOrders(@AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.ok(service.getAllOrdersByUserId(user.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderOutputDto> getOrderById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getOrderById(id));
    }

    @GetMapping("/by-number/{number}")
    public ResponseEntity<OrderOutputDto> getOrderByNumber(@PathVariable int number,
                                                           @AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.ok(service.getOrderByUserOrderNumber(user.getId(), number));
    }

    @PatchMapping("/{id}/processing")
    public ResponseEntity<Void> markAsProcessing(@PathVariable UUID id) {
        service.markAsProcessingById(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/shipped")
    public ResponseEntity<Void> markAsShipped(@PathVariable UUID id) {
        service.markAsShippedById(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/executed")
    public ResponseEntity<Void> markAsExecuted(@PathVariable UUID id) {
        service.markAsExecutedById(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/cancelled")
    public ResponseEntity<Void> cancelOrder(@PathVariable UUID id) {
        service.cancelOrderById(id);
        return ResponseEntity.ok().build();
    }


}
