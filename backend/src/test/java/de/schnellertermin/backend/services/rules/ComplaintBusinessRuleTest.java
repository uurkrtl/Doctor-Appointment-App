package de.schnellertermin.backend.services.rules;

import de.schnellertermin.backend.core.exceptions.types.DuplicateRecordException;
import de.schnellertermin.backend.repositories.ComplaintRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ComplaintBusinessRuleTest {
    @InjectMocks
    private ComplaintBusinessRule complaintBusinessRule;

    @Mock
    private ComplaintRepository complaintRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void checkIfComplaintNameExists_whenComplaintNameExist_shouldThrowDuplicateRecordException() {
        // GIVEN
        String existingComplaintName = "Existing Complaint";

        // WHEN
        when(complaintRepository.existsByName(existingComplaintName)).thenReturn(true);

        // THEN
        assertThrows(DuplicateRecordException.class, () -> complaintBusinessRule.checkIfComplaintNameExists(existingComplaintName));
    }
}