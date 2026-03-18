package com.zync.order.service;

import com.zync.order.dto.CreateOrderDto;
import com.zync.order.dto.InventoryDto;
import com.zync.order.dto.OrderMapper;
import com.zync.order.dto.UpdateOrderDto;
import com.zync.order.entity.OrderEntity;
import com.zync.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final WebClient.Builder webClient;

    public String createOrder(CreateOrderDto body) {
        try {
            InventoryDto inventory = webClient.build()
                    .get()
                    .uri("http://inventory-service/api/inventory/" + body.getProductId())
                    .retrieve()
                    .onStatus(status -> status.value() == 404, clientResponse ->
                            Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory not found")))
                    .bodyToMono(InventoryDto.class)
                    .block();

            if (inventory != null && inventory.getQuantity() < body.getQuantity()) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Out of stock");
            }

            orderRepository.save(orderMapper.toEntity(body));

            return webClient.build()
                    .get()
                    .uri("http://payment-service/api/payment")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create order " + e.getMessage());
        }
    }

    public OrderEntity getOrder(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"Order with that ID does not exist"));
    }

    public void updateOrder(UpdateOrderDto orderPayload) {
        OrderEntity order = orderRepository.findById(orderPayload.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Order not found"
                ));

        try {
            order.setPaymentSuccess(orderPayload.getPaymentSuccess());
            orderRepository.save(order);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update order"
            );
        }
    }
}
