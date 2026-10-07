package com.expensereviewer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expensereviewer.model.Claim;

public interface ClaimRepository extends JpaRepository<Claim, Long> {

    List<Claim> findByCategory(String category);

    List<Claim> findByStatus(String status);

    List<Claim> findByClaimant(String claimant);
}