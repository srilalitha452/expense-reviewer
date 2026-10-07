package com.expensereviewer.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.expensereviewer.model.Claim;

public class ClaimDTO {

    private Long id;
    private String claimant;
    private LocalDate date;
    private String category;
    private BigDecimal amount;
    private String currency;
    private String description;
    private boolean receiptAvailable;
    private String status;

    public ClaimDTO() {
    }

    public ClaimDTO(Long id, String claimant, LocalDate date,
                    String category, BigDecimal amount, String currency,
                    String description, boolean receiptAvailable,
                    String status) {

        this.id = id;
        this.claimant = claimant;
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.receiptAvailable = receiptAvailable;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getClaimant() {
        return claimant;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getDescription() {
        return description;
    }

    public boolean isReceiptAvailable() {
        return receiptAvailable;
    }

    public String getStatus() {
        return status;
    }


    public static ClaimDTO fromClaim(Claim claim) {

    return new ClaimDTO(
        claim.getId(),
        claim.getClaimant(),
        claim.getDate(),
        claim.getCategory(),
        claim.getAmount(),
        claim.getCurrency(),
        claim.getDescription(),
        claim.isReceiptAvailable(),
        claim.getStatus()
    );
 }

}