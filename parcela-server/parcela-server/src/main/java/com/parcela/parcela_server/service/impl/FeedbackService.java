package com.parcela.parcela_server.service.impl;

import com.parcela.parcela_server.dto.FeedbackDto;
import com.parcela.parcela_server.entity.Booking;
import com.parcela.parcela_server.entity.*;
import com.parcela.parcela_server.exception.CustomException;
import com.parcela.parcela_server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public Feedback addFeedback(FeedbackDto feedbackDto) {
        Customer customer = customerRepository.findById(feedbackDto.getCustomerId())
                .orElseThrow(() -> new CustomException("Customer not found"));

        Booking booking = bookingRepository.findById(feedbackDto.getOrderId())
                .orElseThrow(() -> new CustomException("Booking not found"));

        if (!booking.getCustomer().equals(customer)) {
            throw new CustomException("You can only add feedback for your own bookings");
        }

        if (!"DELIVERED".equals(booking.getOrderStatus())) {
            throw new CustomException("Feedback can only be added for DELIVERED bookings");
        }

        Feedback feedback = new Feedback();
        feedback.setDescription(feedbackDto.getDescription());
        feedback.setCustomer(customer);
        feedback.setBooking(booking);

        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }
}