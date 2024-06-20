package de.schnellertermin.backend.repositories;

import de.schnellertermin.backend.models.Complaint;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ComplaintRepository extends MongoRepository<Complaint, String> {
    boolean existsByName(String complaintName);
}
