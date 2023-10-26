package uk.ac.imperial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import uk.ac.imperial.entity.ObservationEntity;

public interface ObservationRepository extends JpaRepository<ObservationEntity, Integer> {

}
