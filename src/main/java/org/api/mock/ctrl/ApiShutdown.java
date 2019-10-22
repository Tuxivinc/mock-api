package org.api.mock.ctrl;

import io.swagger.annotations.*;
import org.api.mock.model.MockResponseGeneric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Api shutdown (control exit application).
 */
@RestController
@RequestMapping("/shutdown")
public class ApiShutdown {

    private static final Logger LOG = LoggerFactory.getLogger(ApiShutdown.class);

    /**
     * Shutdown response entity.
     *
     * @return the response entity
     */
    @PostMapping(value = "/{exitCode}", produces = "application/json")
    @ApiOperation(value = "shutdown", nickname = "close jvm")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exitCode", value = "Return exit code", required = true, dataType = "long", paramType = "path", defaultValue = "0")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = String.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public ResponseEntity<MockResponseGeneric> shutdown(@PathVariable int exitCode) {
        LOG.info("Shutdown required by call API");
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            System.exit(exitCode);
        });
        thread.setContextClassLoader(getClass().getClassLoader());
        thread.start();
        return new ResponseEntity<>(new MockResponseGeneric("Shutdown application with exit code " + exitCode + ", bye..."), HttpStatus.OK);
    }

}
