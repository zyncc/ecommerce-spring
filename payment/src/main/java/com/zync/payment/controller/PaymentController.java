package com.zync.payment.controller;

import com.zync.payment.dto.OrderResponseDto;
import com.zync.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/webhook")
    public ResponseEntity<String> paymentSuccessWebhook(@Valid @RequestBody OrderResponseDto order) {
        paymentService.handlePaymentWebhook(order);
        return ResponseEntity.ok("Webhook successful");
    }

    @GetMapping
    public ResponseEntity<String> generatePaymentUrl() {
        String id = UUID.randomUUID().toString();
        return ResponseEntity.ok("https://stripe.payments.com/" + id);
    }
}
