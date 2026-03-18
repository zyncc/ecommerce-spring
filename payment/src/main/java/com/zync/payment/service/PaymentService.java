package com.zync.payment.service;

import com.zync.payment.dto.OrderResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final WebClient.Builder webClient;

    public void handlePaymentWebhook(OrderResponseDto order) {
        // update inventory and order
        webClient.build()
                 .patch()
                 .uri("http://inventory-service/api/inventory")
                 .bodyValue(order)
                 .retrieve()
                 .onStatus(HttpStatusCode::isError, response ->
                        Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update inventory"))
                 )
                 .toBodilessEntity()
                 .block();

        webClient.build()
                .patch()
                .uri("http://order-service/api/order")
                .bodyValue(order)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update order"))
                )
                .toBodilessEntity()
                .block();
    }
}
