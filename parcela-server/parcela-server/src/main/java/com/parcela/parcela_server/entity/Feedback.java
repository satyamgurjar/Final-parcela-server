package com.parcela.parcela_server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Feedback {
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getFeedbackTime() {
		return feedbackTime;
	}

	public void setFeedbackTime(LocalDateTime feedbackTime) {
		this.feedbackTime = feedbackTime;
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
    private String description;
    private LocalDateTime feedbackTime = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "cust_id")
    @JsonIgnore
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Booking booking;
}