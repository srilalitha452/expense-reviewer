package com.expensereviewer.service;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.expensereviewer.exception.ClaimNotFoundException;
import com.expensereviewer.model.Claim;
import com.expensereviewer.repository.ClaimRepository;

public class ClaimServiceTest {

    @Test
    void findByIdShouldReturnClaim() {

        ClaimRepository repository = mock(ClaimRepository.class);
        PolicyValidationService policyService =
                mock(PolicyValidationService.class);

        ClaimService service =
                new ClaimService(repository, policyService);

        Claim claim = new Claim();

        when(repository.findById(1L))
                .thenReturn(Optional.of(claim));

        Claim result = service.findById(1L);

        assertEquals(claim, result);
    }

    @Test
    void findByIdShouldThrowExceptionWhenClaimNotFound() {

        ClaimRepository repository = mock(ClaimRepository.class);
        PolicyValidationService policyService =
                mock(PolicyValidationService.class);

        ClaimService service =
                new ClaimService(repository, policyService);

        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        try {
            service.findById(99L);
        } catch (ClaimNotFoundException e) {
            assertEquals(
                "Claim not found with id: 99",
                e.getMessage()
            );
        }
    }
}