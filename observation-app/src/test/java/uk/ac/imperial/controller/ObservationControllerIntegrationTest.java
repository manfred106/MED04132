package uk.ac.imperial.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;

import javax.annotation.Resource;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import uk.ac.imperial.controller.api.CreateObservationRequest;
import uk.ac.imperial.controller.api.ErrorResponse;
import uk.ac.imperial.controller.api.UpdateObservationRequest;
import uk.ac.imperial.domain.Observation;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integrationTest")
class ObservationControllerIntegrationTest {

    private static final String BASE_URL = "/api/observation/";

    private static final Integer NON_EXIST_ID = 0;

    private static final Observation DOMAIN_OBSERVATION = Observation.builder()
        .patient(100)
        .date("2023-09-07T11:23:24")
        .value(37.2)
        .measureType("skin-temperature")
        .measureUnit("degrees Celsius")
        .build();

    @Resource
    private TestRestTemplate testRestTemplate;

    private HttpHeaders headers;

    @BeforeEach
    void setUp() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    @DisplayName("Success: Get all Observations - Return OK with all observations")
    void whenGetAllObservationsThenReturnOkAndAllObservations() {

        // When
        ResponseEntity<List<Observation>> responseEntity = testRestTemplate.exchange(
            BASE_URL,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<>() {});

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Success: Create Observation - Return OK with the created observation")
    void whenCreateObservationThenReturnOKWithCreatedObservation() {

        CreateObservationRequest createObservationRequest = CreateObservationRequest.builder()
            .observation(DOMAIN_OBSERVATION)
            .build();

        ResponseEntity<Observation> responseEntity = testRestTemplate.exchange(
            BASE_URL,
            HttpMethod.POST,
            new HttpEntity<>(createObservationRequest, headers),
            Observation.class);

        // Then
        Observation actualObservation = responseEntity.getBody();
        Observation actualObservationWithoutId = actualObservation.withId(null);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actualObservation.getId()).isGreaterThan(0);
        assertThat(actualObservationWithoutId).isEqualTo(DOMAIN_OBSERVATION);
    }

    @Test
    @DisplayName("Failure: Create Observation with invalid fields - Return Bad Request with error messages")
    void whenCreateObservationWithInvalidFieldsThenReturnBadRequestWithErrorMessages() {

        CreateObservationRequest createObservationRequest = CreateObservationRequest.builder()
            .observation(with4InvalidFields(DOMAIN_OBSERVATION))
            .build();

        ResponseEntity<ErrorResponse> responseEntity = testRestTemplate.exchange(
            BASE_URL,
            HttpMethod.POST,
            new HttpEntity<>(createObservationRequest, headers),
            ErrorResponse.class);

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody().getMessages().size()).isEqualTo(4);
    }

    @Test
    @DisplayName("Success: Update Observation - Return OK with the updated observation")
    void whenUpdateObservationByIdThenReturnOKWithUpdatedObservation() {

        Observation observation = createObservation(DOMAIN_OBSERVATION).getBody();
        Observation expectedObservation = observation.withValue(37.6)
            .withPatient(999)
            .withDate("2027-01-01T01:00:00")
            .withValue(38.9);

        UpdateObservationRequest updateObservationRequest = UpdateObservationRequest.builder()
            .observation(expectedObservation)
            .build();

        // When
        var updateUrl = BASE_URL + observation.getId();
        ResponseEntity<Observation> responseEntity = testRestTemplate.exchange(
            updateUrl,
            HttpMethod.PUT,
            new HttpEntity<>(updateObservationRequest, headers),
            Observation.class);

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(expectedObservation);
    }

    @Test
    @DisplayName("Failure: Update Observation by non exist Id - Return Bad Request with error messages")
    void whenUpdateObservationWithInvalidFieldsThenReturnBadRequestWithErrorMessages() {

        Observation newObservation = createObservation(DOMAIN_OBSERVATION).getBody();

        UpdateObservationRequest updateObservationRequest = UpdateObservationRequest.builder()
            .observation(with4InvalidFields(newObservation))
            .build();

        // When
        var updateUrl = BASE_URL + NON_EXIST_ID;
        ResponseEntity<ErrorResponse> responseEntity = testRestTemplate.exchange(
            updateUrl,
            HttpMethod.PUT,
            new HttpEntity<>(updateObservationRequest, headers),
            ErrorResponse.class);

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody().getMessages().size()).isEqualTo(4);
    }
    
    @Test
    @DisplayName("Failure: Update Observation with invalid fields - Return Not Found with an error message")
    void whenUpdateObservationByNonExistIdThenReturnNotFoundWithErrorMessages() {

        UpdateObservationRequest updateObservationRequest = UpdateObservationRequest.builder()
            .observation(DOMAIN_OBSERVATION)
            .build();

        // When
        var updateUrl = BASE_URL + NON_EXIST_ID;
        ResponseEntity<ErrorResponse> responseEntity = testRestTemplate.exchange(
            updateUrl,
            HttpMethod.PUT,
            new HttpEntity<>(updateObservationRequest, headers),
            ErrorResponse.class);

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody().getMessages().size()).isEqualTo(1);
    }
    
    @Test
    @DisplayName("Success: Delete Observation by Id - Return OK with the deleted observation")
    void whenDeleteObservationByIdThenReturnOKWithDeletedObservation() {

        Observation observation = createObservation(DOMAIN_OBSERVATION).getBody();

        // When
        var deleteUrl = BASE_URL + observation.getId();
        ResponseEntity<Observation> responseEntity = testRestTemplate.exchange(
            deleteUrl,
            HttpMethod.DELETE,
            null,
            Observation.class);

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(observation);
    }

    @Test
    @DisplayName("Failure: Delete Observation by non exist Id - Return Not Found with an error message")
    void whenDeleteObservationByNonExistIdThenReturnNotFoundWithErrorMessage() {

        // When
        var deleteUrl = BASE_URL + NON_EXIST_ID;
        ResponseEntity<ErrorResponse> responseEntity = testRestTemplate.exchange(
            deleteUrl,
            HttpMethod.DELETE,
            null,
            ErrorResponse.class);

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody().getMessages().size()).isEqualTo(1);
    }

    private Observation with4InvalidFields(Observation observation) {
        return observation
            .withPatient(null)
            .withValue(null)
            .withMeasureType(null)
            .withMeasureUnit(null);
    }

    /**
     * Create Observation into database. Created observation will be used by update and delete operations.
     */
    private ResponseEntity<Observation> createObservation(Observation observation) {

        CreateObservationRequest createObservationRequest = CreateObservationRequest.builder()
            .observation(observation)
            .build();

        return testRestTemplate.exchange(
            BASE_URL,
            HttpMethod.POST,
            new HttpEntity<>(createObservationRequest, headers),
            Observation.class);
    }

}