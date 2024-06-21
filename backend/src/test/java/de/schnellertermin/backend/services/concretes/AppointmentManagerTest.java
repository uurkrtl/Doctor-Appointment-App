package de.schnellertermin.backend.services.concretes;

import de.schnellertermin.backend.core.mappers.ModelMapperService;
import de.schnellertermin.backend.models.Appointment;
import de.schnellertermin.backend.models.Complaint;
import de.schnellertermin.backend.models.enums.AppointmentStatus;
import de.schnellertermin.backend.repositories.AppointmentRepository;
import de.schnellertermin.backend.repositories.ComplaintRepository;
import de.schnellertermin.backend.services.abstracts.IdService;
import de.schnellertermin.backend.services.dtos.requests.AppointmentRequest;
import de.schnellertermin.backend.services.dtos.responses.AppointmentCreatedResponse;
import de.schnellertermin.backend.services.dtos.responses.AppointmentGetAllResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.util.List;
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
    private OpenAiManager openAiManager;

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
    void getAllAppointments_shouldReturnsListOfAppointments() {
        // GIVEN
        List<Appointment> appointments = List.of(
                Appointment.builder().id("1").build(),
                Appointment.builder().id("2").build()
        );

        // WHEN
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(appointmentRepository.findAll()).thenReturn(appointments);

        List<AppointmentGetAllResponse> actualResponse = appointmentManager.getAllAppointments();

        // THEN
        assertEquals(2, actualResponse.size());
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

    @Test
    void updateAppointmentScore_whenRequestIsValid_shouldReturnAppointmentCreatedResponse() throws IOException {
        // GIVEN
        String appointmentId = "testId";
        String imageUrl = "testImageUrl";
        String description = "testDescription";
        String openAiResult = "1 point";

        AppointmentCreatedResponse expectedResponse = AppointmentCreatedResponse.builder()
                .description(description)
                .urgencyScore(Integer.valueOf(openAiResult.substring(0,1)))
                .build();
        Appointment appointment = Appointment.builder().build();

        // WHEN
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(appointment, AppointmentCreatedResponse.class)).thenReturn(expectedResponse);
        when(appointmentRepository.save(appointment)).thenReturn(appointment);
        when(openAiManager.sendRequest(imageUrl, description)).thenReturn(openAiResult);
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));

        AppointmentCreatedResponse actualResponse = appointmentManager.updateAppointmentScore(appointmentId, imageUrl, description);

        // THEN
        verify(appointmentRepository, times(1)).save(appointment);
        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getDescription(), actualResponse.getDescription());
        assertEquals(expectedResponse.getUrgencyScore(), actualResponse.getUrgencyScore());
    }

    @Test
    void updateAppointmentStatus_whenRequestIsValid_shouldReturnAppointmentCreatedResponse() {
        // GIVEN
        String appointmentId = "testId";
        String status = "ACTIVE";

        AppointmentCreatedResponse expectedResponse = AppointmentCreatedResponse.builder()
                .status(AppointmentStatus.ACTIVE)
                .build();
        Appointment appointment = Appointment.builder().build();

        // WHEN
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(appointment, AppointmentCreatedResponse.class)).thenReturn(expectedResponse);
        when(appointmentRepository.save(appointment)).thenReturn(appointment);
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));

        AppointmentCreatedResponse actualResponse = appointmentManager.updateAppointmentStatus(appointmentId, status);

        // THEN
        verify(appointmentRepository, times(1)).save(appointment);
        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
    }

}