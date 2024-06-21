package de.schnellertermin.backend.controllers;

import de.schnellertermin.backend.services.abstracts.ComplaintService;
import de.schnellertermin.backend.services.dtos.requests.ComplaintRequest;
import de.schnellertermin.backend.services.dtos.responses.ComplaintCreatedResponse;
import de.schnellertermin.backend.services.dtos.responses.ComplaintGetAllResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
@RequiredArgsConstructor
public class ComplaintController {
    private final ComplaintService complaintService;

    @GetMapping
    List<ComplaintGetAllResponse> getAllComplaints() {
        return complaintService.getAllComplaints();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ComplaintCreatedResponse addComplaint(@Valid @RequestBody ComplaintRequest complaintRequest) {
        return complaintService.addComplaint(complaintRequest);
    }
}
