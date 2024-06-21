package de.schnellertermin.backend.services.concretes;

import de.schnellertermin.backend.core.exceptions.types.RecordNotFoundException;
import de.schnellertermin.backend.core.mappers.ModelMapperService;
import de.schnellertermin.backend.models.Category;
import de.schnellertermin.backend.models.Complaint;
import de.schnellertermin.backend.repositories.CategoryRepository;
import de.schnellertermin.backend.repositories.ComplaintRepository;
import de.schnellertermin.backend.services.abstracts.ComplaintService;
import de.schnellertermin.backend.services.abstracts.IdService;
import de.schnellertermin.backend.services.dtos.requests.ComplaintRequest;
import de.schnellertermin.backend.services.dtos.responses.ComplaintCreatedResponse;
import de.schnellertermin.backend.services.dtos.responses.ComplaintGetAllResponse;
import de.schnellertermin.backend.services.messages.CategoryMessage;
import de.schnellertermin.backend.services.rules.ComplaintBusinessRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComplaintManager implements ComplaintService {
    private final ComplaintRepository complaintRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapperService modelMapperService;
    private final IdService idService;
    private final ComplaintBusinessRule complaintBusinessRule;

    @Override
    public List<ComplaintGetAllResponse> getAllComplaints() {
        List<Complaint> complaints = complaintRepository.findAll();
        return complaints.stream().map(complaint -> modelMapperService.forResponse().map(complaint, ComplaintGetAllResponse.class)).toList();
    }

    @Override
    public ComplaintCreatedResponse addComplaint(ComplaintRequest complaintRequest) {
        complaintBusinessRule.checkIfComplaintNameExists(complaintRequest.getName());
        Complaint complaint = modelMapperService.forRequest().map(complaintRequest, Complaint.class);
        Category selectedCategory = categoryRepository.findById(complaintRequest.getCategoryId()).orElseThrow(() -> new RecordNotFoundException(CategoryMessage.CATEGORY_NOT_FOUND));
        complaint.setId(idService.generateComplaintId());
        complaint.setCategory(selectedCategory);
        complaint = complaintRepository.save(complaint);
        return modelMapperService.forResponse().map(complaint, ComplaintCreatedResponse.class);
    }
}
