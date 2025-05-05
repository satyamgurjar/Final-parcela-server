package com.parcela.parcela_server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Booking {
    public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public double getParcelWeight() {
		return parcelWeight;
	}

	public void setParcelWeight(double parcelWeight) {
		this.parcelWeight = parcelWeight;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPackingPreference() {
		return packingPreference;
	}

	public void setPackingPreference(String packingPreference) {
		this.packingPreference = packingPreference;
	}

	public LocalDateTime getPickupTime() {
		return pickupTime;
	}

	public void setPickupTime(LocalDateTime pickupTime) {
		this.pickupTime = pickupTime;
	}

	public LocalDateTime getDropoffTime() {
		return dropoffTime;
	}

	public void setDropoffTime(LocalDateTime dropoffTime) {
		this.dropoffTime = dropoffTime;
	}

	public double getServiceCost() {
		return serviceCost;
	}

	public void setServiceCost(double serviceCost) {
		this.serviceCost = serviceCost;
	}

	public LocalDateTime getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(LocalDateTime paymentTime) {
		this.paymentTime = paymentTime;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Feedback getFeedback() {
		return feedback;
	}

	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private String receiverName;
    private String address;
    private String pincode;
    private String mobileNumber;
    private double parcelWeight;
    private String description;
    private String packingPreference;
    private LocalDateTime pickupTime;
    private LocalDateTime dropoffTime;
    private double serviceCost;
    private LocalDateTime paymentTime;
    private String orderStatus = "PENDING"; // PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED

    @ManyToOne
    @JoinColumn(name = "cust_id")
    @JsonIgnore
    private Customer customer;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    @JsonIgnore
    private Payment payment;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    @JsonIgnore
    private Feedback feedback;
}