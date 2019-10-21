package org.api.mock.ctrl;

import io.swagger.annotations.*;
import org.api.mock.model.MockResponseGeneric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Api mock healthcheck.
 */
@RestController
@RequestMapping("/healthcheck")
public class ApiMockHealthCheck {

    private static final Logger LOG = LoggerFactory.getLogger(ApiMockHealthCheck.class);
    private static HttpStatus httpStatus = HttpStatus.OK;

    /**
     * Sets code return.
     *
     * @param statusCode the status code
     * @return the code
     */
    @RequestMapping(value = "/setcode/{statusCode}", method = {RequestMethod.POST}, produces = "application/json")
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
        LOG.debug("set http status to {}", httpStatus.toString());
        return new ResponseEntity<>(new MockResponseGeneric("Status are change to " + httpStatus.getReasonPhrase()), HttpStatus.OK);
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    @RequestMapping(value = "", method = {RequestMethod.GET}, produces = "application/json")
    @ApiOperation(value = "getStatus", nickname = "get status code")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = String.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public ResponseEntity<MockResponseGeneric> getStatus() {
        return new ResponseEntity<>(new MockResponseGeneric("Status is " + httpStatus.getReasonPhrase()), httpStatus);
    }

}
