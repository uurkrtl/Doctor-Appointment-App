package de.schnellertermin.backend.controllers;

import de.schnellertermin.backend.services.abstracts.ComplaintService;
import de.schnellertermin.backend.services.dtos.requests.ComplaintRequest;
import de.schnellertermin.backend.services.dtos.responses.ComplaintCreatedResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/complaints")
@RequiredArgsConstructor
public class ComplaintController {
    private final ComplaintService complaintService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ComplaintCreatedResponse addComplaint(@Valid @RequestBody ComplaintRequest complaintRequest) {
        return complaintService.addComplaint(complaintRequest);
    }
}
