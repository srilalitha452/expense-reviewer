package com.expensereviewer.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expensereviewer.ai.GeminiService;

@RestController
@RequestMapping("/api/ai")
public class AIReviewController {

    private final GeminiService geminiService;

    public AIReviewController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping("/review")
    public String reviewClaim(@RequestBody AIReviewRequest request) {

        return geminiService.reviewClaim(
                request.claimant(),
                request.category(),
                request.amount(),
                request.currency(),
                request.description(),
                request.receiptAvailable()
        );
    }

    public record AIReviewRequest(
            String claimant,
            String category,
            String amount,
            String currency,
            String description,
            boolean receiptAvailable
    ) {
    }
}