package com.expensereviewer.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expensereviewer.model.Claim;
import com.expensereviewer.service.PolicyValidationService;

@RestController
@RequestMapping("/api/policy")
public class PolicyValidationController {

    private final PolicyValidationService policyValidationService;

    public PolicyValidationController(
            PolicyValidationService policyValidationService) {
        this.policyValidationService = policyValidationService;
    }

    @PostMapping("/validate")
    public List<String> validateClaim(@RequestBody Claim claim) {

        return policyValidationService.validateClaim(claim);
    }
}
