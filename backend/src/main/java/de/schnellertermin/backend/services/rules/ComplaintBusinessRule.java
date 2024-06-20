package de.schnellertermin.backend.services.rules;

import de.schnellertermin.backend.core.exceptions.types.DuplicateRecordException;
import de.schnellertermin.backend.repositories.ComplaintRepository;
import de.schnellertermin.backend.services.messages.ComplaintMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComplaintBusinessRule {
    private final ComplaintRepository complaintRepository;

    public void checkIfComplaintNameExists(String complaintName) {
        if(complaintRepository.existsByName(complaintName)) {
            throw new DuplicateRecordException(ComplaintMessage.COMPLAINT_NAME_EXISTS);
        }
    }
}
