package com.parcela.parcela_server.service.impl;

import com.parcela.parcela_server.entity.Booking;
import com.parcela.parcela_server.entity.Customer;
import com.parcela.parcela_server.dto.BookingDto;
import com.parcela.parcela_server.exception.CustomException;
import com.parcela.parcela_server.repository.BookingRepository;
import com.parcela.parcela_server.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public Booking addBooking(BookingDto bookingDto) {
        Customer customer = customerRepository.findById(bookingDto.getCustomerId())
                .orElseThrow(() -> new CustomException("Customer not found"));

        Booking booking = new Booking();
        booking.setReceiverName(bookingDto.getReceiverName());
        booking.setAddress(bookingDto.getAddress());
        booking.setPincode(bookingDto.getPincode());
        booking.setMobileNumber(bookingDto.getMobileNumber());
        booking.setParcelWeight(bookingDto.getParcelWeight());
        booking.setDescription(bookingDto.getDescription());
        booking.setPackingPreference(bookingDto.getPackingPreference());
        booking.setPickupTime(bookingDto.getPickupTime());
        booking.setDropoffTime(bookingDto.getDropoffTime());
        booking.setServiceCost(bookingDto.getServiceCost());
        booking.setCustomer(customer);

        return bookingRepository.save(booking);
    }

    public List<Booking> getUserBookings(String customerId) {
        Customer customer = customerRepository.findById(Long.valueOf(customerId))
                .orElseThrow(() -> new CustomException("Customer not found"));
        return bookingRepository.findByCustomer(customer);
    }

    public Optional<Booking> getBookingById(Long orderId, Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomException("Customer not found"));
        return bookingRepository.findById(orderId)
                .filter(booking -> booking.getCustomer().equals(customer));
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> getDeliveredBookings() {
        return bookingRepository.findByOrderStatus("DELIVERED");
    }

    public Booking updateBooking(Long orderId, BookingDto bookingDto) {
        Booking existingBooking = bookingRepository.findById(orderId)
                .orElseThrow(() -> new CustomException("Booking not found"));

        if (!"PENDING".equals(existingBooking.getOrderStatus()) && !"PROCESSING".equals(existingBooking.getOrderStatus())) {
            throw new CustomException("Only bookings with PENDING or PROCESSING status can be updated");
        }

        existingBooking.setPickupTime(bookingDto.getPickupTime());
        existingBooking.setDropoffTime(bookingDto.getDropoffTime());
        return bookingRepository.save(existingBooking);
    }

    public String cancelBooking(Long orderId, Long customerId) {
        Booking booking = bookingRepository.findById(orderId)
                .orElseThrow(() -> new CustomException("Booking not found"));

        if (!booking.getCustomer().getCustId().equals(customerId)) {
            throw new CustomException("You can only cancel your own bookings");
        }

        if (!"PENDING".equals(booking.getOrderStatus()) && !"PROCESSING".equals(booking.getOrderStatus())) {
            throw new CustomException("Only bookings with PENDING or PROCESSING status can be cancelled");
        }

        booking.setOrderStatus("CANCELLED");
        bookingRepository.save(booking);
        return "Booking cancelled successfully";
    }

    public String adminCancelBooking(Long orderId) {
        Booking booking = bookingRepository.findById(orderId)
                .orElseThrow(() -> new CustomException("Booking not found"));

        if (!"PENDING".equals(booking.getOrderStatus()) && !"PROCESSING".equals(booking.getOrderStatus())) {
            throw new CustomException("Only bookings with PENDING or PROCESSING status can be cancelled");
        }

        booking.setOrderStatus("CANCELLED");
        bookingRepository.save(booking);
        return "Booking cancelled successfully by admin";
    }

    public Booking updateOrderStatus(Long orderId, String status) {
        Booking booking = bookingRepository.findById(orderId)
                .orElseThrow(() -> new CustomException("Booking not found"));

        booking.setOrderStatus(status);
        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByStatus(String status) {
        return bookingRepository.findByOrderStatus(status);
    }

    public List<Booking> getBookingsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return bookingRepository.findByPickupTimeBetween(startDate, endDate);
    }

    public Map<String, Object> getDashboardStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        List<Booking> allBookings = bookingRepository.findAll();
        List<Customer> allCustomers = customerRepository.findAll();
        
        statistics.put("totalBookings", allBookings.size());
        statistics.put("totalCustomers", allCustomers.size());
        statistics.put("totalRevenue", allBookings.stream()
                .filter(b -> b.getOrderStatus().equals("DELIVERED"))
                .mapToDouble(Booking::getServiceCost)
                .sum());
        
        // Get recent activities (last 5 bookings)
        List<Booking> recentBookings = allBookings.stream()
                .sorted((b1, b2) -> b2.getPickupTime().compareTo(b1.getPickupTime()))
                .limit(5)
                .collect(Collectors.toList());
        statistics.put("recentActivities", recentBookings);
        
        return statistics;
    }

    public Map<String, Object> getBookingStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        
        // Total bookings
        List<Booking> allBookings = bookingRepository.findAll();
        statistics.put("totalBookings", allBookings.size());
        
        // Bookings by status
        statistics.put("pendingBookings", bookingRepository.findByOrderStatus("PENDING").size());
        statistics.put("processingBookings", bookingRepository.findByOrderStatus("PROCESSING").size());
        statistics.put("shippedBookings", bookingRepository.findByOrderStatus("SHIPPED").size());
        statistics.put("deliveredBookings", bookingRepository.findByOrderStatus("DELIVERED").size());
        statistics.put("cancelledBookings", bookingRepository.findByOrderStatus("CANCELLED").size());
        
        // Average service cost
        double avgCost = allBookings.stream()
                .mapToDouble(Booking::getServiceCost)
                .average()
                .orElse(0.0);
        statistics.put("averageServiceCost", avgCost);
        
        // Total revenue
        double totalRevenue = allBookings.stream()
                .filter(booking -> "DELIVERED".equals(booking.getOrderStatus()))
                .mapToDouble(Booking::getServiceCost)
                .sum();
        statistics.put("totalRevenue", totalRevenue);
        
        // Recent bookings (last 7 days)
        LocalDateTime weekAgo = LocalDateTime.now().minusDays(7);
        long recentBookings = allBookings.stream()
                .filter(booking -> booking.getPickupTime().isAfter(weekAgo))
                .count();
        statistics.put("recentBookings", recentBookings);
        
        return statistics;
    }

    public Booking getBookingByOrderId(Long orderId) {
        return bookingRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Booking not found with order ID: " + orderId));
    }
}