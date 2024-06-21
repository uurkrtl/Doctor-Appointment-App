package de.schnellertermin.backend.controllers;

import de.schnellertermin.backend.services.abstracts.AppointmentService;
import de.schnellertermin.backend.services.dtos.requests.AppointmentRequest;
import de.schnellertermin.backend.services.dtos.responses.AppointmentCreatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    AppointmentCreatedResponse addAppointmentTask(@RequestBody AppointmentRequest appointmentRequest) {
        return appointmentService.addAppointmentTask(appointmentRequest);
    }
}
