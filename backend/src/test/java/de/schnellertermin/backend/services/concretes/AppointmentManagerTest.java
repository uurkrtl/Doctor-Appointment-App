package de.schnellertermin.backend.services.concretes;

import de.schnellertermin.backend.core.mappers.ModelMapperService;
import de.schnellertermin.backend.models.Appointment;
import de.schnellertermin.backend.models.Complaint;
import de.schnellertermin.backend.repositories.AppointmentRepository;
import de.schnellertermin.backend.repositories.ComplaintRepository;
import de.schnellertermin.backend.services.abstracts.IdService;
import de.schnellertermin.backend.services.dtos.requests.AppointmentRequest;
import de.schnellertermin.backend.services.dtos.responses.AppointmentCreatedResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class AppointmentManagerTest {
    @InjectMocks
    private AppointmentManager appointmentManager;
    private ModelMapper modelMapper;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private ComplaintRepository complaintRepository;

    @Mock
    private ModelMapperService modelMapperService;

    @Mock
    private IdService idService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        modelMapper = mock(ModelMapper.class);
    }

    @Test
    void addAppointment_whenRequestIsValid_shouldReturnAppointmentCreatedResponse() {
        // GIVEN
        AppointmentRequest appointmentRequest = AppointmentRequest.builder().complaintId("1").build();
        AppointmentCreatedResponse expectedResponse = AppointmentCreatedResponse.builder().build();
        Appointment appointment = Appointment.builder().build();

        // WHEN
        when(idService.generateCategoryId()).thenReturn("1");
        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(appointmentRequest, Appointment.class)).thenReturn(appointment);
        when(modelMapper.map(appointment, AppointmentCreatedResponse.class)).thenReturn(expectedResponse);
        when(appointmentRepository.save(appointment)).thenReturn(appointment);
        when(complaintRepository.findById("1")).thenReturn(Optional.of(Complaint.builder().build()));

        AppointmentCreatedResponse actualResponse = appointmentManager.addAppointmentTask(appointmentRequest);

        // THEN
        verify(appointmentRepository, times(1)).save(appointment);
        assertEquals(expectedResponse.getId(), actualResponse.getId());
    }

}