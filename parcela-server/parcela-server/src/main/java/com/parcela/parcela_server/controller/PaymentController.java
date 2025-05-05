package com.parcela.parcela_server.controller;

import com.parcela.parcela_server.dto.PaymentDto;
import com.parcela.parcela_server.entity.Payment;
import com.parcela.parcela_server.service.impl.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<?> makePayment(@RequestBody PaymentDto paymentDto) {
        try {
            Payment payment = paymentService.makePayment(paymentDto);
            return ResponseEntity.ok(payment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}