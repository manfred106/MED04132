package uk.ac.imperial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import uk.ac.imperial.entity.MeasurementEntity;

public interface MeasurementRepository extends JpaRepository<MeasurementEntity, Integer> {

    MeasurementEntity getByTypeAndUnit(String type, String unit);

}
