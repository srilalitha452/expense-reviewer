package com.expensereviewer.ai;

import org.springframework.stereotype.Service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

@Service
public class GeminiService {

    private final Client client;

    public GeminiService() {
        String apiKey = System.getenv("GEMINI_API_KEY");

        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                    "GEMINI_API_KEY environment variable is not set"
            );
        }

        client = Client.builder()
                .apiKey(apiKey)
                .build();
    }

    public String reviewClaim(
            String claimant,
            String category,
            String amount,
            String currency,
            String description,
            boolean receiptAvailable) {

        String prompt = """
                You are an expense review assistant.

                Review the following expense claim.

                Claimant: %s
                Category: %s
                Amount: %s %s
                Description: %s
                Receipt Available: %s

                Analyze the claim and return a concise review.

                Use exactly this format:

                Recommendation: APPROVE, REJECT, or REVIEW
                Risk Level: LOW, MEDIUM, or HIGH
                Reason: <short reason>

                Important:
                You are only making a recommendation.
                A human reviewer must make the final decision.
                """.formatted(
                claimant,
                category,
                amount,
                currency,
                description,
                receiptAvailable
        ); 
         
        GenerateContentResponse response =
        client.models.generateContent(
                "gemini-3.7-flash",
                prompt,
                null
        );

         return response.text();

    }
}