package de.schnellertermin.backend.services.abstracts;

import de.schnellertermin.backend.services.dtos.requests.ComplaintRequest;
import de.schnellertermin.backend.services.dtos.responses.ComplaintCreatedResponse;

public interface ComplaintService {
    ComplaintCreatedResponse addComplaint (ComplaintRequest complaintRequest);
}
