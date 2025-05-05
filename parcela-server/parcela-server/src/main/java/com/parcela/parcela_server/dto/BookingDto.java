package com.parcela.parcela_server.dto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingDto {
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
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
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
    private Long customerId;
}