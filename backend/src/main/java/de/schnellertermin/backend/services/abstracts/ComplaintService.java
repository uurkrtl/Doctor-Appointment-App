package de.schnellertermin.backend.services.abstracts;

import de.schnellertermin.backend.services.dtos.requests.ComplaintRequest;
import de.schnellertermin.backend.services.dtos.responses.ComplaintCreatedResponse;
import de.schnellertermin.backend.services.dtos.responses.ComplaintGetAllResponse;

import java.util.List;

public interface ComplaintService {
    List<ComplaintGetAllResponse> getAllComplaints();
    List<ComplaintGetAllResponse> getComplaintsByCategoryId(String categoryId);
    ComplaintCreatedResponse addComplaint (ComplaintRequest complaintRequest);
}
