package com.expensereviewer.controller;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expensereviewer.model.Claim;
import com.expensereviewer.service.ClaimService;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/claims")
public class ClaimController {

    private final ClaimService claimService;
    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @GetMapping
    public List<Claim> getAllClaims() {
        return claimService.findAll();
    }

    @PostMapping
    public Claim createClaim(@Valid @RequestBody @NonNull Claim claim) {
        return claimService.save(claim);
    }


    @PutMapping("/{id}/approve")
       public Claim approveClaim(@PathVariable @NonNull Long id) {
      return claimService.approve(id);
    }

   @PutMapping("/{id}/reject")
      public Claim rejectClaim(@PathVariable @NonNull Long id) {
      return claimService.reject(id);
    }


    @GetMapping("/page")
      public Page<Claim> getClaimsWithPagination(Pageable pageable) {
      return claimService.findAll(pageable);
     }



    @GetMapping("/{id}")
      public Claim getClaimById(@PathVariable @NonNull Long id) {
         return claimService.findById(id);
     }

     @PutMapping("/{id}")
      public Claim updateClaim(
        @PathVariable Long id,
        @Valid @RequestBody Claim updatedClaim) {
        return claimService.update(id, updatedClaim);
     }

      @DeleteMapping("/{id}")
         public void deleteClaim(@PathVariable Long id) {
        claimService.delete(id);
     }

     @GetMapping("/category/{category}")
public List<Claim> getClaimsByCategory(@PathVariable String category) {
    return claimService.findByCategory(category);
}

@GetMapping("/status/{status}")
public List<Claim> getClaimsByStatus(@PathVariable String status) {
    return claimService.findByStatus(status);
}

@GetMapping("/claimant/{claimant}")
public List<Claim> getClaimsByClaimant(@PathVariable String claimant) {
    return claimService.findByClaimant(claimant);
}

}
