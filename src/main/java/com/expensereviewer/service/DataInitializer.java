package com.expensereviewer.service;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.expensereviewer.model.PolicyRule;
import com.expensereviewer.repository.PolicyRuleRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final PolicyRuleRepository policyRuleRepository;

    public DataInitializer(PolicyRuleRepository policyRuleRepository) {
        this.policyRuleRepository = policyRuleRepository;
    }

    @Override
    public void run(String... args) {

        if (policyRuleRepository.count() == 0) {

            PolicyRule clientMeal = new PolicyRule();
            clientMeal.setCategory("Client Meal");
            clientMeal.setMaxAmount(new BigDecimal("3000"));
            clientMeal.setCurrency("INR");
            clientMeal.setReceiptRequired(true);

            policyRuleRepository.save(clientMeal);

            PolicyRule travel = new PolicyRule();
            travel.setCategory("Travel");
            travel.setMaxAmount(new BigDecimal("10000"));
            travel.setCurrency("INR");
            travel.setReceiptRequired(true);

            policyRuleRepository.save(travel);

            PolicyRule hotel = new PolicyRule();
            hotel.setCategory("Hotel");
            hotel.setMaxAmount(new BigDecimal("8000"));
            hotel.setCurrency("INR");
            hotel.setReceiptRequired(true);

            policyRuleRepository.save(hotel);

            PolicyRule officeSupplies = new PolicyRule();
            officeSupplies.setCategory("Office Supplies");
            officeSupplies.setMaxAmount(new BigDecimal("5000"));
            officeSupplies.setCurrency("INR");
            officeSupplies.setReceiptRequired(true);

            policyRuleRepository.save(officeSupplies);
        }
    }
}