package de.schnellertermin.backend.services.concretes;

import de.schnellertermin.backend.core.mappers.ModelMapperService;
import de.schnellertermin.backend.models.Category;
import de.schnellertermin.backend.models.Complaint;
import de.schnellertermin.backend.repositories.CategoryRepository;
import de.schnellertermin.backend.repositories.ComplaintRepository;
import de.schnellertermin.backend.services.abstracts.IdService;
import de.schnellertermin.backend.services.dtos.requests.ComplaintRequest;
import de.schnellertermin.backend.services.dtos.responses.ComplaintCreatedResponse;
import de.schnellertermin.backend.services.dtos.responses.ComplaintGetAllResponse;
import de.schnellertermin.backend.services.rules.ComplaintBusinessRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class ComplaintManagerTest {
    @InjectMocks
    private ComplaintManager complaintManager;
    private ModelMapper modelMapper;

    @Mock
    private ComplaintRepository complaintRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ModelMapperService modelMapperService;

    @Mock
    private IdService idService;

    @Mock
    @SuppressWarnings("unused")
    private ComplaintBusinessRule complaintBusinessRule;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        modelMapper = mock(ModelMapper.class);
    }

    @Test
    void getAllComplaints_shouldReturnsListOfComplaints() {
        // GIVEN
        List<Complaint> complaints = List.of(
                Complaint.builder().id("1").build(),
                Complaint.builder().id("2").build()
        );

        // WHEN
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(complaintRepository.findAll()).thenReturn(complaints);

        List<ComplaintGetAllResponse> actualResponse = complaintManager.getAllComplaints();

        // THEN
        assertEquals(2, actualResponse.size());
    }

    @Test
    void addComplaint_whenRequestIsValid_shouldReturnComplaintCreatedResponse() {
        // GIVEN
        ComplaintRequest complaintRequest = ComplaintRequest.builder().name("Test Complaint").categoryId("1").build();
        ComplaintCreatedResponse expectedResponse = ComplaintCreatedResponse.builder().name("Test Complaint").build();
        Complaint complaint = Complaint.builder().build();

        // WHEN
        when(idService.generateComplaintId()).thenReturn("1");
        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(complaintRequest, Complaint.class)).thenReturn(complaint);
        when(modelMapper.map(complaint, ComplaintCreatedResponse.class)).thenReturn(expectedResponse);
        when(complaintRepository.save(complaint)).thenReturn(complaint);
        when(categoryRepository.findById("1")).thenReturn(Optional.of(Category.builder().id("1").build()));

        ComplaintCreatedResponse actualResponse = complaintManager.addComplaint(complaintRequest);

        // THEN
        verify(complaintRepository, times(1)).save(complaint);
        assertEquals(expectedResponse.getId(), actualResponse.getId());
    }

}