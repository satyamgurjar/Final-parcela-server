package com.parcela.parcela_server.controller;

import com.parcela.parcela_server.dto.FeedbackDto;
import com.parcela.parcela_server.entity.Feedback;
import com.parcela.parcela_server.service.impl.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<Feedback> addFeedback(@RequestBody FeedbackDto feedbackDto) {
        return ResponseEntity.ok(feedbackService.addFeedback(feedbackDto));
    }

    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedbacks() {
        return ResponseEntity.ok(feedbackService.getAllFeedbacks());
    }
}
