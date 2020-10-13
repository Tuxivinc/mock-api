package org.api.mock.ctrl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.api.mock.model.MockResponseGeneric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * The type Api mock healthcheck.
 */
@RestController
@Tag(name = "HealthCheck", description = "HealthCheck call")
@RequestMapping("/healthcheck")
public class ApiMockHealthCheck {

    private static final Logger LOG = LoggerFactory.getLogger(ApiMockHealthCheck.class);
    private HttpStatus httpStatus = HttpStatus.OK;

    /**
     * Sets code return.
     *
     * @param statusCode the status code
     * @return the code
     */
    @PostMapping(value = "/setcode/{statusCode}", produces = "application/json")
    @Operation(summary = "Set Http Status code return when call healthchek")
    public ResponseEntity<MockResponseGeneric> setCode(@NotBlank @PathVariable int statusCode) {
        httpStatus = HttpStatus.valueOf(statusCode);
        LOG.debug("set http status to {}", httpStatus);
        return new ResponseEntity<>(new MockResponseGeneric("Status are change to " + httpStatus.getReasonPhrase()), HttpStatus.OK);
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    @GetMapping(value = "", produces = "application/json")
    @Operation(summary = "get status code")
    public ResponseEntity<MockResponseGeneric> getStatus() {
        return new ResponseEntity<>(new MockResponseGeneric("Status is " + httpStatus.getReasonPhrase()), httpStatus);
    }

}
