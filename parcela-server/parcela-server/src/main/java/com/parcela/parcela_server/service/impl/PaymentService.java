package com.parcela.parcela_server.service.impl;

import com.parcela.parcela_server.dto.PaymentDto;
import com.parcela.parcela_server.entity.*;
import com.parcela.parcela_server.exception.CustomException;
import com.parcela.parcela_server.repository.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {

    // Hardcoded valid card details
    private static final String VALID_CARD_NUMBER = "1234567890";
    private static final String VALID_CARD_HOLDER = "John Doe";
    private static final String VALID_EXPIRY = "12/25";
    private static final String VALID_CVV = "123";

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public Payment makePayment(PaymentDto paymentDto) {
        // Validate customer
        Customer customer = customerRepository.findById(paymentDto.getCustomerId())
                .orElseThrow(() -> new CustomException("Customer not found"));

        // Get the booking (should be in PENDING state)
        Booking booking = bookingRepository.findById(paymentDto.getOrderId())
                .orElseThrow(() -> new CustomException("Booking not found"));

        // Validate booking belongs to customer
        if (!booking.getCustomer().getCustId().equals(customer.getCustId())) {
            throw new CustomException("You can only pay for your own bookings");
        }

        // Validate booking status
        if (!"PENDING".equals(booking.getOrderStatus())) {
            throw new CustomException("Payment can only be made for PENDING bookings");
        }

        // Validate card details
        if (!VALID_CARD_NUMBER.equals(paymentDto.getCardNumber()) ||
                !VALID_CARD_HOLDER.equals(paymentDto.getCardHolderName()) ||
                !VALID_EXPIRY.equals(paymentDto.getExpiryDate()) ||
                !VALID_CVV.equals(paymentDto.getCvv())) {
            throw new CustomException("Payment failed: Invalid card details");
        }

        try {
            // Create and save payment
            Payment payment = new Payment();
            payment.setAmount(booking.getServiceCost());
            payment.setStatus("COMPLETED");
            payment.setCustomer(customer);
            payment.setBooking(booking);
            Payment savedPayment = paymentRepository.save(payment);

            // Update booking status and payment time
            booking.setPaymentTime(LocalDateTime.now());
            booking.setOrderStatus("PROCESSING");
            bookingRepository.save(booking);

            return savedPayment;
        } catch (Exception e) {
            // In case of any error during payment processing
            throw new CustomException("Payment processing failed: " + e.getMessage());
        }
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new CustomException("Payment not found"));
    }

    public List<Payment> getPaymentsByStatus(String status) {
        return paymentRepository.findByStatus(status);
    }

    public Map<String, Object> getPaymentStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        
        // Total payments
        List<Payment> allPayments = paymentRepository.findAll();
        statistics.put("totalPayments", allPayments.size());
        
        // Total revenue
        double totalRevenue = allPayments.stream()
                .filter(payment -> "COMPLETED".equals(payment.getStatus()))
                .mapToDouble(Payment::getAmount)
                .sum();
        statistics.put("totalRevenue", totalRevenue);
        
        // Payments by status
        statistics.put("completedPayments", paymentRepository.findByStatus("COMPLETED").size());
        statistics.put("pendingPayments", paymentRepository.findByStatus("PENDING").size());
        statistics.put("failedPayments", paymentRepository.findByStatus("FAILED").size());
        
        // Average payment amount
        double avgAmount = allPayments.stream()
                .filter(payment -> "COMPLETED".equals(payment.getStatus()))
                .mapToDouble(Payment::getAmount)
                .average()
                .orElse(0.0);
        statistics.put("averagePaymentAmount", avgAmount);
        
        return statistics;
    }
}
