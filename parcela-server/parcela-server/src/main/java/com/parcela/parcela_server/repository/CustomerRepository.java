package com.parcela.parcela_server.repository;

import com.parcela.parcela_server.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByCustIdAndPassword(Long custId, String password);
    Optional<Customer> findByEmailAndPassword(String email, String password);
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByCustId(Long custId);
}