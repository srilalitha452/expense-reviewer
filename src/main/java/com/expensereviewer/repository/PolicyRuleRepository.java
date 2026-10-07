package com.expensereviewer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expensereviewer.model.PolicyRule;

public interface PolicyRuleRepository extends JpaRepository<PolicyRule, Long> {

    Optional<PolicyRule> findByCategory(String category);
}