package de.schnellertermin.backend.controllers;

import de.schnellertermin.backend.services.abstracts.AppointmentService;
import de.schnellertermin.backend.services.dtos.requests.AppointmentRequest;
import de.schnellertermin.backend.services.dtos.responses.AppointmentCreatedResponse;
import de.schnellertermin.backend.services.dtos.responses.AppointmentGetAllResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    @GetMapping
    List<AppointmentGetAllResponse> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    AppointmentCreatedResponse addAppointmentTask(@RequestBody AppointmentRequest appointmentRequest) {
        return appointmentService.addAppointmentTask(appointmentRequest);
    }

    @PutMapping("/set-score/{id}")
    AppointmentCreatedResponse updateAppointmentScore(@PathVariable String id, @RequestParam String imageUrl, @RequestParam String description) throws IOException {
        return appointmentService.updateAppointmentScore(id, imageUrl, description);
    }

    @PutMapping("/set-status/{id}")
    AppointmentCreatedResponse updateAppointmentStatus(@PathVariable String id, @RequestParam String status) {
        return appointmentService.updateAppointmentStatus(id, status);
    }
}
