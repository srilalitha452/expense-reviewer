package com.expensereviewer.model;
import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
@Entity
@Table(name = "claims")
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   @NotBlank
    private String claimant;
 
   @NotNull
     private LocalDate date;

   @NotBlank
      private String category;

   @NotNull
   @Positive
     private BigDecimal amount;

    @NotBlank
    private String currency;

    private String description;

    private boolean receiptAvailable;

    private String status = "PENDING";

    public Claim() {
    }

    public Long getId() {
        return id;
    }

    public String getClaimant() {
        return claimant;
    }

    public void setClaimant(String claimant) {
        this.claimant = claimant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isReceiptAvailable() {
        return receiptAvailable;
    }

    public void setReceiptAvailable(boolean receiptAvailable) {
        this.receiptAvailable = receiptAvailable;
    
    }

    public String getStatus() {
          return status;
    }

    public void setStatus(String status) {
          this.status = status;
    }

}
