package de.schnellertermin.backend.services.concretes;

import de.schnellertermin.backend.services.abstracts.IdService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IdManager implements IdService {
    @Override
    public String generateCategoryId() {
        return "CAT-" + UUID.randomUUID();
    }

    @Override
    public String generateComplaintId() {
        return "COM-" + UUID.randomUUID();
    }
}
