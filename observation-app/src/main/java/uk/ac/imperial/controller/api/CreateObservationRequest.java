package uk.ac.imperial.controller.api;

import javax.validation.Valid;

import lombok.*;

import lombok.extern.jackson.Jacksonized;
import uk.ac.imperial.domain.Observation;

@Getter
@Builder
@Jacksonized
public class CreateObservationRequest implements ObservationRequest {

    @Valid
    private Observation observation;

}
