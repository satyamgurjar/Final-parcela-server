package com.parcela.parcela_server.entity;

import javax.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Payment {
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getTimeDate() {
		return timeDate;
	}

	public void setTimeDate(LocalDateTime timeDate) {
		this.timeDate = timeDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime timeDate = LocalDateTime.now();
    private double amount;
    private String status = "PENDING"; // PENDING, COMPLETED, FAILED

    @ManyToOne
    @JoinColumn(name = "cust_id")
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Booking booking;
}
