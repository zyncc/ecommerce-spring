package com.zync.order.controller;

import com.zync.order.dto.CreateOrderDto;
import com.zync.order.dto.UpdateOrderDto;
import com.zync.order.entity.OrderEntity;
import com.zync.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderEntity> getOrder(@PathVariable UUID orderId) {
        OrderEntity findOrder = orderService.getOrder(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(findOrder);
    }

    @PatchMapping
    public ResponseEntity<String> updateOrder(@RequestBody UpdateOrderDto orderPayload) {
        orderService.updateOrder(orderPayload);
        return ResponseEntity.ok("Update order");
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@Valid  @RequestBody CreateOrderDto body) {
        try {
            String paymentLink = orderService.createOrder(body);
            return ResponseEntity.status(HttpStatus.CREATED).body("Payment Link: " + paymentLink);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
