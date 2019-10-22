package org.api.mock.ctrl;

import io.swagger.annotations.*;
import org.api.mock.model.MockResponseGeneric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The type Api mock healthcheck.
 */
@RestController
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
    @ApiOperation(value = "setCode", nickname = "set code return when call healthchek")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "statusCode", value = "New status return", required = true, dataType = "long", paramType = "path", defaultValue = "200")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = String.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public ResponseEntity<MockResponseGeneric> setCode(@PathVariable int statusCode) {
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
    @ApiOperation(value = "getStatus", nickname = "get status code")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = String.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public ResponseEntity<MockResponseGeneric> getStatus() {
        return new ResponseEntity<>(new MockResponseGeneric("Status is " + httpStatus.getReasonPhrase()), httpStatus);
    }

}
