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
import de.schnellertermin.backend.services.dtos.responses.AppointmentGetAllResponse;
import de.schnellertermin.backend.services.messages.AppointmentMessage;
import de.schnellertermin.backend.services.messages.ComplaintMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentManager implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final ModelMapperService modelMapperService;
    private final IdService idService;
    private final ComplaintRepository complaintRepository;
    private final OpenAiManager openAiManager;

    @Override
    public List<AppointmentGetAllResponse> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        return appointments.stream().map(appointment -> modelMapperService.forResponse().map(appointment, AppointmentGetAllResponse.class)).toList();
    }

    @Override
    public AppointmentCreatedResponse getAppointmentById(String id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(AppointmentMessage.APPOINTMENT_NOT_FOUND));
        return modelMapperService.forResponse().map(appointment, AppointmentCreatedResponse.class);
    }

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

    @Override
    public AppointmentCreatedResponse updateAppointmentScore(String id, String imageUrl, String description) throws IOException {
        Appointment selectedAppointment = appointmentRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(AppointmentMessage.APPOINTMENT_NOT_FOUND));

        String openAiResult = openAiManager.sendRequest(imageUrl, description);
        Integer score = Integer.valueOf(openAiResult.substring(0,1));

        selectedAppointment.setUrgencyScore(score);
        selectedAppointment.setImageUrl(imageUrl);
        selectedAppointment.setDescription(description);
        selectedAppointment.setUpdatedAt(LocalDateTime.now());
        selectedAppointment = appointmentRepository.save(selectedAppointment);
        return modelMapperService.forResponse().map(selectedAppointment, AppointmentCreatedResponse.class);
    }

    @Override
    public AppointmentCreatedResponse updateAppointmentStatus(String id, String status) {
        Appointment selectedAppointment = appointmentRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(AppointmentMessage.APPOINTMENT_NOT_FOUND));
        selectedAppointment.setStatus(AppointmentStatus.valueOf(status));
        selectedAppointment = appointmentRepository.save(selectedAppointment);
        return modelMapperService.forResponse().map(selectedAppointment, AppointmentCreatedResponse.class);
    }
}
