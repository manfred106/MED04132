package uk.ac.imperial.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uk.ac.imperial.controller.api.CreateObservationRequest;
import uk.ac.imperial.controller.api.UpdateObservationRequest;
import uk.ac.imperial.domain.*;
import uk.ac.imperial.service.ObservationService;

@RestController
@RequestMapping("/api/observation")
public class ObservationController {

    @Resource
    private ObservationService observationService;

    @GetMapping("/")
    public ResponseEntity<List<Observation>> getAllObservations() {
        List<Observation> observations = observationService.getAllObservations();
        return ResponseEntity.ok(observations);
    }

    @PostMapping
    public ResponseEntity<Observation> createObservation(
        @RequestBody @Valid CreateObservationRequest createObservationRequest) {

        Observation createdObservation = observationService.createObservation(createObservationRequest.getObservation());
        return ResponseEntity.ok(createdObservation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Observation> updateObservation(
        @PathVariable Integer id,
        @RequestBody @Valid UpdateObservationRequest updateObservationRequest) {

        Observation observation = updateObservationRequest.getObservation().withId(id);
        Observation updatedObservation = observationService.updateObservation(observation);
        return ResponseEntity.ok(updatedObservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Observation> deleteObservationById(
        @PathVariable @Min(value = 0) Integer id) {

        Observation updatedObservation = observationService.deleteObservationById(id);
        return ResponseEntity.ok(updatedObservation);
    }
}
