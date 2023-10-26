package uk.ac.imperial.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.*;
import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import uk.ac.imperial.domain.*;
import uk.ac.imperial.entity.ObservationEntity;
import uk.ac.imperial.mapper.ObservationMapper;
import uk.ac.imperial.repository.ObservationRepository;

@Service
public class ObservationService {

    @Resource
    private ObservationMapper observationMapper;

    @Resource
    private ObservationRepository observationRepository;


    public List<Observation> getAllObservations() {
        return observationRepository.findAll().stream()
            .map(observationMapper::fromEntity)
            .collect(Collectors.toList());
    }

    public Observation createObservation(Observation observation) {
        ObservationEntity observationEntity = observationMapper.toEntity(observation);
        observationEntity = observationRepository.save(observationEntity);
        observationEntity = observationRepository.getById(observationEntity.getId());
        return observationMapper.fromEntity(observationEntity);
    }

    public Observation updateObservation(Observation observation) {
        ObservationEntity observationEntity = observationRepository.findById(observation.getId())
            .orElseThrow(EntityNotFoundException::new);

        observationEntity = observationMapper.toEntity(observation);
        observationEntity = observationRepository.save(observationEntity);
        observationEntity = observationRepository.getById(observationEntity.getId());

        return observationMapper.fromEntity(observationEntity);
    }

    public Observation deleteObservationById(Integer id) {

        ObservationEntity observationEntity = observationRepository
            .findById(id)
            .orElseThrow(EntityNotFoundException::new);

        observationRepository.deleteById(id);

        return observationMapper.fromEntity(observationEntity);
    }

}
