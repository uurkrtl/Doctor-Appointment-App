package de.schnellertermin.backend.services.concretes;

import de.schnellertermin.backend.core.exceptions.types.RecordNotFoundException;
import de.schnellertermin.backend.core.mappers.ModelMapperService;
import de.schnellertermin.backend.models.Appointment;
import de.schnellertermin.backend.models.Complaint;
import de.schnellertermin.backend.models.enums.AppointmentStatus;
import de.schnellertermin.backend.repositories.AppointmentRepository;
import de.schnellertermin.backend.repositories.ComplaintRepository;
import de.schnellertermin.backend.services.abstracts.AppointmentService;
import de.schnellertermin.backend.services.abstracts.IdService;
import de.schnellertermin.backend.services.dtos.requests.AppointmentRequest;
import de.schnellertermin.backend.services.dtos.responses.AppointmentCreatedResponse;
import de.schnellertermin.backend.services.messages.ComplaintMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AppointmentManager implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final ModelMapperService modelMapperService;
    private final IdService idService;
    private final ComplaintRepository complaintRepository;

    @Override
    public AppointmentCreatedResponse addAppointmentTask(AppointmentRequest appointmentRequest) {
        Appointment appointment = modelMapperService.forRequest().map(appointmentRequest, Appointment.class);
        Complaint selectedComplaint = complaintRepository.findById(appointmentRequest.getComplaintId()).orElseThrow(() -> new RecordNotFoundException(ComplaintMessage.COMPLAINT_NOT_FOUND));
        appointment.setId(idService.generateAppointmentId());
        appointment.setStatus(AppointmentStatus.DRAFT);
        appointment.setCreatedAt(LocalDateTime.now());
        appointment.setComplaint(selectedComplaint);
        appointment = appointmentRepository.save(appointment);
        return modelMapperService.forResponse().map(appointment, AppointmentCreatedResponse.class);
    }
}
