package com.parcela.parcela_server.controller;

import com.parcela.parcela_server.entity.Booking;
import com.parcela.parcela_server.entity.Feedback;
import com.parcela.parcela_server.entity.Customer;
import com.parcela.parcela_server.entity.Payment;
import com.parcela.parcela_server.service.impl.BookingService;
import com.parcela.parcela_server.service.impl.FeedbackService;
import com.parcela.parcela_server.service.impl.CustomerService;
import com.parcela.parcela_server.service.impl.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PaymentService paymentService;

    // Customer Management APIs
    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long customerId) {
        return ResponseEntity.ok(customerService.getCustomerById(customerId));
    }

    @PutMapping("/customers/{customerId}")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable Long customerId,
            @RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.updateCustomer(customerId, customer));
    }

    @DeleteMapping("/customers/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(customerService.deleteCustomer(customerId));
    }

    // Payment Management APIs
    @GetMapping("/payments")
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/payments/{paymentId}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long paymentId) {
        return ResponseEntity.ok(paymentService.getPaymentById(paymentId));
    }

    @GetMapping("/payments/status/{status}")
    public ResponseEntity<List<Payment>> getPaymentsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(paymentService.getPaymentsByStatus(status));
    }

    @GetMapping("/payments/statistics")
    public ResponseEntity<Map<String, Object>> getPaymentStatistics() {
        return ResponseEntity.ok(paymentService.getPaymentStatistics());
    }

    // Booking Management APIs
    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/bookings/status/{status}")
    public ResponseEntity<List<Booking>> getBookingsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(bookingService.getBookingsByStatus(status));
    }

    @GetMapping("/bookings/date-range")
    public ResponseEntity<List<Booking>> getBookingsByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        return ResponseEntity.ok(bookingService.getBookingsByDateRange(startDate, endDate));
    }

    @GetMapping("/bookings/statistics")
    public ResponseEntity<Map<String, Object>> getBookingStatistics() {
        return ResponseEntity.ok(bookingService.getBookingStatistics());
    }

    @GetMapping("/bookings/{orderId}")
    public ResponseEntity<Booking> getBookingByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(bookingService.getBookingByOrderId(orderId));
    }

    // Dashboard Statistics
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getDashboardStatistics() {
        return ResponseEntity.ok(bookingService.getDashboardStatistics());
    }
}