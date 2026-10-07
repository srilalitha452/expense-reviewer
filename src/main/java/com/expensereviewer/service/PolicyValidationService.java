package com.expensereviewer.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.expensereviewer.model.Claim;
import com.expensereviewer.model.PolicyRule;
import com.expensereviewer.repository.PolicyRuleRepository;

@Service
public class PolicyValidationService {

    private final PolicyRuleRepository policyRuleRepository;

    public PolicyValidationService(PolicyRuleRepository policyRuleRepository) {
        this.policyRuleRepository = policyRuleRepository;
    }

    public List<String> validateClaim(Claim claim) {

        List<String> issues = new ArrayList<>();

        // Check required fields
        if (claim.getClaimant() == null || claim.getClaimant().isBlank()) {
            issues.add("Claimant is required.");
        }

        if (claim.getDate() == null) {
            issues.add("Date is required.");
        }

        if (claim.getCategory() == null || claim.getCategory().isBlank()) {
            issues.add("Category is required.");
        }

        if (claim.getAmount() == null) {
            issues.add("Amount is required.");
        } else if (claim.getAmount().signum() <= 0) {
            issues.add("Amount must be greater than zero.");
        }

        if (claim.getCurrency() == null || claim.getCurrency().isBlank()) {
            issues.add("Currency is required.");
        }

        // Stop here if category is missing
        if (claim.getCategory() == null || claim.getCategory().isBlank()) {
            return issues;
        }

        // Find policy for this category
        PolicyRule rule = policyRuleRepository
                .findByCategory(claim.getCategory())
                .orElse(null);

        if (rule == null) {
            issues.add("No policy rule found for category: " + claim.getCategory());
            return issues;
        }

        // Check currency
        if (claim.getCurrency() != null &&
                !claim.getCurrency().equalsIgnoreCase(rule.getCurrency())) {

            issues.add("Currency does not match the policy.");
        }

        // Check maximum amount
        if (claim.getAmount() != null &&
                claim.getAmount().compareTo(rule.getMaxAmount()) > 0) {

            issues.add(
                "Amount exceeds the " +
                claim.getCategory() +
                " limit of " +
                rule.getMaxAmount() +
                " " +
                rule.getCurrency() +
                "."
            );
        }

        // Check receipt
        if (rule.isReceiptRequired() && !claim.isReceiptAvailable()) {
            issues.add("Receipt is required for this category.");
        }

        return issues;
    }
}