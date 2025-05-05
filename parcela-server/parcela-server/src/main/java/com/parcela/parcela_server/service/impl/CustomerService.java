package com.parcela.parcela_server.service.impl;

import com.parcela.parcela_server.entity.Customer;
import com.parcela.parcela_server.exception.CustomException;
import com.parcela.parcela_server.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomException("Customer not found"));
    }

    public Customer updateCustomer(Long customerId, Customer updatedCustomer) {
        Customer existingCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomException("Customer not found"));

        existingCustomer.setName(updatedCustomer.getName());
        existingCustomer.setEmail(updatedCustomer.getEmail());
        existingCustomer.setMobileNumber(updatedCustomer.getMobileNumber());
        existingCustomer.setAddress(updatedCustomer.getAddress());

        return customerRepository.save(existingCustomer);
    }

    public String deleteCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomException("Customer not found"));

        customerRepository.delete(customer);
        return "Customer deleted successfully";
    }
} 