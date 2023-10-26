package uk.ac.imperial.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

import uk.ac.imperial.domain.Observation;
import uk.ac.imperial.entity.*;
import uk.ac.imperial.repository.MeasurementRepository;

@ExtendWith(MockitoExtension.class)
class ObservationMapperTest {

    private static final Observation DOMAIN_OBSERVATION = Observation.builder()
        .id(1)
        .date("2023-09-06T11:02:44")
        .patient(101)
        .value(65.0)
        .measureType("heart-rate")
        .measureUnit("beats/minute")
        .build();

    private static final MeasurementEntity ENTITY_MEASUREMENT = MeasurementEntity.builder()
        .id(1)
        .type("heart-rate")
        .unit("beats/minute")
        .build();

    private static final ObservationEntity ENTITY_OBSERVATION = ObservationEntity.builder()
        .id(1)
        .date(LocalDateTime.parse("2023-09-06T11:02:44", DateTimeFormatter.ISO_DATE_TIME))
        .patient(101)
        .value(65.0)
        .measurementEntity(ENTITY_MEASUREMENT)
        .build();

    @Mock
    private MeasurementRepository measurementRepository;

    @InjectMocks
    private ObservationMapper observationMapper;

    @Test
    void whenToEntityThenEntityIsReturned() {

        // Given
        when(measurementRepository.getByTypeAndUnit("heart-rate", "beats/minute")).thenReturn(ENTITY_MEASUREMENT);

        // When
        ObservationEntity observationEntity = observationMapper.toEntity(DOMAIN_OBSERVATION);

        // Then
        assertThat(observationEntity).isEqualTo(ENTITY_OBSERVATION);
    }

    @Test
    void testFromEntity() {

        // When
        Observation observation = observationMapper.fromEntity(ENTITY_OBSERVATION);

        // Then
        assertThat(observation).isEqualTo(DOMAIN_OBSERVATION);
    }

}