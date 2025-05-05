package com.parcela.parcela_server.repository;


import com.parcela.parcela_server.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findAllByOrderByIdDesc();
}