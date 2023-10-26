package uk.ac.imperial.controller.api;

import java.util.List;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Builder
@Getter
@Jacksonized
public class ErrorResponse {

    private List<String> messages;

}
