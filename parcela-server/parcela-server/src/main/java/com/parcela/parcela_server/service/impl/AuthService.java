package com.parcela.parcela_server.service.impl;

import com.parcela.parcela_server.dto.LoginRequest;
import com.parcela.parcela_server.entity.Customer;
import com.parcela.parcela_server.exception.CustomException;
import com.parcela.parcela_server.repository.CustomerRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer register(Customer customer) {
        if (customerRepository.findByCustId(customer.getCustId()).isPresent()) {
            throw new CustomException("Customer ID already registered");
        }
        return customerRepository.save(customer);
    }


    public Customer login(LoginRequest loginRequest) {
        Long customerId = loginRequest.getCustomerId();
        System.out.println("Attempting login with Customer ID: " + customerId);
        
        Customer customer = customerRepository.findByCustId(customerId)
            .orElseThrow(() -> new RuntimeException("Customer ID not found"));

        if (!customer.getPassword().equals(loginRequest.getPassword())) {
            throw new RuntimeException("Password mismatch");
        }

        return customer;
    }
}
