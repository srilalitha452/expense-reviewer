package com.expensereviewer.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.expensereviewer.exception.ClaimNotFoundException;
import com.expensereviewer.model.Claim;
import com.expensereviewer.repository.ClaimRepository;

@Service
public class ClaimService {

    private final ClaimRepository claimRepository;
    private final PolicyValidationService policyValidationService;

    public ClaimService(
            ClaimRepository claimRepository,
            PolicyValidationService policyValidationService) {

        this.claimRepository = claimRepository;
        this.policyValidationService = policyValidationService;
    }

    public Claim save(@NonNull Claim claim) {

        List<String> issues =
                policyValidationService.validateClaim(claim);

        System.out.println("Policy validation results:");

        if (issues.isEmpty()) {
            System.out.println("Claim passed policy validation.");
        } else {
            for (String issue : issues) {
                System.out.println("- " + issue);
            }
        }

        return claimRepository.save(claim);
    }

    public List<Claim> findAll() {
        return claimRepository.findAll();
    }

    public Page<Claim> findAll(Pageable pageable) {
         return claimRepository.findAll(pageable);
    }

    public Claim findById(@NonNull Long id) {
       return claimRepository.findById(id)
          .orElseThrow(() -> new ClaimNotFoundException(id));
   }


   public Claim update(Long id, Claim updatedClaim) {
    Claim claim = claimRepository.findById(id)
            .orElseThrow(() -> new ClaimNotFoundException(id));
    claim.setClaimant(updatedClaim.getClaimant());
    claim.setDate(updatedClaim.getDate());
    claim.setCategory(updatedClaim.getCategory());
    claim.setAmount(updatedClaim.getAmount());
    claim.setCurrency(updatedClaim.getCurrency());
    claim.setDescription(updatedClaim.getDescription());
    claim.setReceiptAvailable(updatedClaim.isReceiptAvailable());
    return claimRepository.save(claim);
    }


    public void delete(Long id) {
    Claim claim = claimRepository.findById(id)
            .orElseThrow(() -> new ClaimNotFoundException(id));
    claimRepository.delete(claim);
    }


    public Claim approve(Long id) {
    Claim claim = claimRepository.findById(id)
            .orElseThrow(() -> new ClaimNotFoundException(id));
    claim.setStatus("APPROVED");
    return claimRepository.save(claim);
     }

     public Claim reject(Long id) {
    Claim claim = claimRepository.findById(id)
            .orElseThrow(() -> new ClaimNotFoundException(id));

    claim.setStatus("REJECTED");
    return claimRepository.save(claim);
    }


    public List<Claim> findByCategory(String category) {
    return claimRepository.findByCategory(category);
}

public List<Claim> findByStatus(String status) {
    return claimRepository.findByStatus(status);
}

public List<Claim> findByClaimant(String claimant) {
    return claimRepository.findByClaimant(claimant);
}



}