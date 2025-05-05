package com.parcela.parcela_server.controller;

import com.parcela.parcela_server.dto.BookingDto;
import com.parcela.parcela_server.entity.Booking;
import com.parcela.parcela_server.service.impl.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> addBooking(@RequestBody BookingDto bookingDto) {
        return ResponseEntity.ok(bookingService.addBooking(bookingDto));
    }

    @GetMapping("/user/{customerId}")
    public ResponseEntity<List<Booking>> getUserBookings(@PathVariable String customerId) {
        return ResponseEntity.ok(bookingService.getUserBookings(customerId));
    }

    @GetMapping("/user/{customerId}/{orderId}")
    public ResponseEntity<Optional<Booking>> getBookingById(@PathVariable Long orderId, @PathVariable Long customerId) {
        return ResponseEntity.ok(bookingService.getBookingById(orderId, customerId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/delivered")
    public ResponseEntity<List<Booking>> getDeliveredBookings() {
        return ResponseEntity.ok(bookingService.getDeliveredBookings());
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long orderId, @RequestBody BookingDto bookingDto) {
        return ResponseEntity.ok(bookingService.updateBooking(orderId, bookingDto));
    }

    @DeleteMapping("/user/{orderId}/{customerId}")
    public ResponseEntity<String> cancelBooking(@PathVariable Long orderId, @PathVariable Long customerId) {
        return ResponseEntity.ok(bookingService.cancelBooking(orderId, customerId));
    }

    @DeleteMapping("/admin/{orderId}")
    public ResponseEntity<String> adminCancelBooking(@PathVariable Long orderId) {
        return ResponseEntity.ok(bookingService.adminCancelBooking(orderId));
    }

    @PutMapping("/status/{orderId}")
    public ResponseEntity<Booking> updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
        return ResponseEntity.ok(bookingService.updateOrderStatus(orderId, status));
    }
}