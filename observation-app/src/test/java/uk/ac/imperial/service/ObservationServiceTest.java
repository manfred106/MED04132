package uk.ac.imperial.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import uk.ac.imperial.domain.Observation;
import uk.ac.imperial.entity.*;
import uk.ac.imperial.mapper.ObservationMapper;
import uk.ac.imperial.repository.ObservationRepository;

@ExtendWith(MockitoExtension.class)
class ObservationServiceTest {

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
    private ObservationMapper observationMapper;

    @Mock
    private ObservationRepository observationRepository;

    @InjectMocks
    private ObservationService observationService;

    @Test
    void whenGetAllObservationsThenReturnListOfObservations() {

        // Given
        when(observationRepository.findAll()).thenReturn(List.of(ENTITY_OBSERVATION));
        when(observationMapper.fromEntity(ENTITY_OBSERVATION)).thenReturn(DOMAIN_OBSERVATION);

        // When
        List<Observation> observations = observationService.getAllObservations();

        // Then
        assertThat(observations).isEqualTo(List.of(DOMAIN_OBSERVATION));
        verify(observationRepository).findAll();
        verify(observationMapper).fromEntity(ENTITY_OBSERVATION);
    }

    @Test
    void whenCreateObservationThenReturnNewObservation() {

        // Given
        when(observationMapper.toEntity(DOMAIN_OBSERVATION)).thenReturn(ENTITY_OBSERVATION);
        when(observationRepository.save(ENTITY_OBSERVATION)).thenReturn(ENTITY_OBSERVATION);
        when(observationRepository.getById(ENTITY_OBSERVATION.getId())).thenReturn(ENTITY_OBSERVATION);
        when(observationMapper.fromEntity(ENTITY_OBSERVATION)).thenReturn(DOMAIN_OBSERVATION);

        // When
        Observation createdObservation = observationService.createObservation(DOMAIN_OBSERVATION);

        // Then
        assertThat(createdObservation).isEqualTo(DOMAIN_OBSERVATION);
        verify(observationMapper).toEntity(DOMAIN_OBSERVATION);
        verify(observationRepository).save(ENTITY_OBSERVATION);
        verify(observationRepository).getById(ENTITY_OBSERVATION.getId());
        verify(observationMapper).fromEntity(ENTITY_OBSERVATION);
    }

    @Test
    void whenUpdateObservationByIdThenReturnUpdatedObservation() {

        // Given
        when(observationRepository.findById(ENTITY_OBSERVATION.getId())).thenReturn(Optional.of(ENTITY_OBSERVATION));
        when(observationMapper.toEntity(DOMAIN_OBSERVATION)).thenReturn(ENTITY_OBSERVATION);
        when(observationRepository.save(ENTITY_OBSERVATION)).thenReturn(ENTITY_OBSERVATION);
        when(observationRepository.getById(ENTITY_OBSERVATION.getId())).thenReturn(ENTITY_OBSERVATION);
        when(observationMapper.fromEntity(ENTITY_OBSERVATION)).thenReturn(DOMAIN_OBSERVATION);

        // Act
        Observation updatedObservation = observationService.updateObservation(DOMAIN_OBSERVATION);

        // Assert
        assertThat(updatedObservation).isEqualTo(DOMAIN_OBSERVATION);
        verify(observationRepository).findById(ENTITY_OBSERVATION.getId());
        verify(observationRepository).getById(ENTITY_OBSERVATION.getId());
        verify(observationMapper).fromEntity(ENTITY_OBSERVATION);
    }

    @Test
    void whenUpdateObservationByNonExistIdThenThrowEntityNotFoundException() {

        // Given
        when(observationRepository.findById(ENTITY_OBSERVATION.getId())).thenReturn(Optional.empty());

        // When
        assertThrows(EntityNotFoundException.class,
            () -> observationService.updateObservation(DOMAIN_OBSERVATION));

        // Then
        verify(observationRepository).findById(ENTITY_OBSERVATION.getId());
        verify(observationRepository, never()).save(ENTITY_OBSERVATION);

    }

    @Test
    void whenDeleteObservationByIdThenReturnDeletedObservation() {

        // Given
        when(observationRepository.findById(ENTITY_OBSERVATION.getId())).thenReturn(Optional.of(ENTITY_OBSERVATION));
        when(observationMapper.fromEntity(ENTITY_OBSERVATION)).thenReturn(DOMAIN_OBSERVATION);

        // When
        Observation deletedObservation = observationService.deleteObservationById(ENTITY_OBSERVATION.getId());

        // Then
        assertThat(deletedObservation).isEqualTo(DOMAIN_OBSERVATION);
        verify(observationRepository).findById(ENTITY_OBSERVATION.getId());
        verify(observationRepository).deleteById(ENTITY_OBSERVATION.getId());
        verify(observationMapper).fromEntity(ENTITY_OBSERVATION);
    }

    @Test
    void whenDeleteObservationByNonExistIdThenThrowEntityNotFoundException() {

        // Given
        when(observationRepository.findById(ENTITY_OBSERVATION.getId())).thenReturn(Optional.empty());

        // When
        assertThrows(EntityNotFoundException.class,
            () -> observationService.deleteObservationById(ENTITY_OBSERVATION.getId()));

        // Then
        verify(observationRepository).findById(ENTITY_OBSERVATION.getId());
        verify(observationRepository, never()).deleteById(ENTITY_OBSERVATION.getId());
    }

}