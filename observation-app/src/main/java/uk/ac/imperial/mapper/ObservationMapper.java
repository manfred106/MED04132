package uk.ac.imperial.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javax.annotation.*;

import org.springframework.stereotype.*;

import uk.ac.imperial.domain.Observation;
import uk.ac.imperial.entity.MeasurementEntity;
import uk.ac.imperial.entity.ObservationEntity;
import uk.ac.imperial.repository.MeasurementRepository;

@Component
public class ObservationMapper {

    @Resource
    private MeasurementRepository measurementRepository;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_DATE_TIME;


    public ObservationEntity toEntity(Observation observation) {
        MeasurementEntity measurementEntity = getMeasurementEntity(observation);

        return ObservationEntity.builder()
            .id(observation.getId())
            .measurementEntity(measurementEntity)
            .date(observation.getDate() != null ? LocalDateTime.parse(observation.getDate(), dateFormatter) : null)
            .patient(observation.getPatient())
            .value(observation.getValue())
            .build();
    }

    public Observation fromEntity(ObservationEntity observationEntity) {
        String dateStr = Optional.ofNullable(observationEntity.getDate())
            .map(dateFormatter::format)
            .orElse(null);

        return Observation.builder()
            .id(observationEntity.getId())
            .date(dateStr)
            .patient(observationEntity.getPatient())
            .value(observationEntity.getValue())
            .measureType(observationEntity.getMeasurementEntity().getType())
            .measureUnit(observationEntity.getMeasurementEntity().getUnit())
            .build();
    }

    private MeasurementEntity getMeasurementEntity(Observation observation) {
        return measurementRepository.getByTypeAndUnit(observation.getMeasureType(), observation.getMeasureUnit());
    }

}