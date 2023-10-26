package uk.ac.imperial.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import uk.ac.imperial.controller.api.*;
import uk.ac.imperial.domain.Observation;
import uk.ac.imperial.entity.*;
import uk.ac.imperial.service.ObservationService;

@ExtendWith(MockitoExtension.class)
class ObservationControllerTest {

    private static final MeasurementEntity ENTITY_MEASUREMENT = MeasurementEntity.builder()
        .id(1)
        .type("skin-temperature")
        .unit("degrees Celsius")
        .build();

    private static final ObservationEntity ENTITY_OBSERVATION = ObservationEntity.builder()
        .patient(100)
        .date(LocalDateTime.of(2023, 9, 7, 11, 23, 24))
        .value(37.2)
        .measurementEntity(ENTITY_MEASUREMENT)
        .build();

    private static final Observation DOMAIN_OBSERVATION = Observation.builder()
        .patient(100)
        .date("2023-09-07T11:23:24")
        .value(37.2)
        .measureType(ENTITY_MEASUREMENT.getType())
        .measureUnit(ENTITY_MEASUREMENT.getUnit())
        .build();
    
    @Mock
    private ObservationService observationService;

    @InjectMocks
    private ObservationController observationController;

    @BeforeEach
    void setUp() {
    }

    @Test
    void whenGetAllObservationsThenReturnListOfObservations() {

        // Given
        List<Observation> expectedObservations = List.of(DOMAIN_OBSERVATION);
        when(observationService.getAllObservations()).thenReturn(expectedObservations);

        // When
        ResponseEntity<List<Observation>> responseEntity = observationController.getAllObservations();

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(expectedObservations);
        verify(observationService).getAllObservations();
    }

    @Test
    void whenGetAllObservationsThenReturnNewObservation() {
        // Given
        when(observationService.createObservation(DOMAIN_OBSERVATION)).thenReturn(DOMAIN_OBSERVATION);

        CreateObservationRequest createObservationRequest = CreateObservationRequest.builder()
            .observation(DOMAIN_OBSERVATION)
            .build();

        // When
        ResponseEntity<Observation> responseEntity = observationController.createObservation(createObservationRequest);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(responseEntity.getBody()).isEqualTo(DOMAIN_OBSERVATION);
        verify(observationService).createObservation(DOMAIN_OBSERVATION);
    }

    @Test
    void whenGetAllObservationsThenReturnUpdatedObservation() {

        // Given
        when(observationService.updateObservation(DOMAIN_OBSERVATION)).thenReturn(DOMAIN_OBSERVATION);

        UpdateObservationRequest updateObservationRequest = UpdateObservationRequest.builder()
            .observation(DOMAIN_OBSERVATION)
            .build();

        // When
        ResponseEntity<Observation> responseEntity = observationController.updateObservation(ENTITY_OBSERVATION.getId(), updateObservationRequest);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(responseEntity.getBody()).isEqualTo(DOMAIN_OBSERVATION);
        verify(observationService).updateObservation(DOMAIN_OBSERVATION);
    }

    @Test
    void whenDeleteObservationByIdThenReturnDeletedObservation() {
        // Given
        when(observationService.deleteObservationById(ENTITY_OBSERVATION.getId())).thenReturn(DOMAIN_OBSERVATION);

        // When
        ResponseEntity<Observation> responseEntity = observationController.deleteObservationById(ENTITY_OBSERVATION.getId());

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(responseEntity.getBody()).isEqualTo(DOMAIN_OBSERVATION);
        verify(observationService).deleteObservationById(ENTITY_OBSERVATION.getId());
    }

}