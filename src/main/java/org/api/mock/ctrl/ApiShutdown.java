package org.api.mock.ctrl;

import io.swagger.annotations.*;
import org.api.mock.model.MockResponseGeneric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Api shutdown (control exit application).
 */
@RestController
@RequestMapping("/shutdown")
public class ApiShutdown {

    private static final Logger LOG = LoggerFactory.getLogger(ApiShutdown.class);

    @Autowired
    private ApplicationContext appContext;

    /**
     * Shutdown response entity.
     *
     * @return the response entity
     */
    @RequestMapping(value = "/{exitCode}", method = {RequestMethod.POST}, produces = "application/json")
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
            //SpringApplication.exit(appContext, () -> exitCode);
            System.exit(exitCode);
        });
        thread.setContextClassLoader(getClass().getClassLoader());
        thread.start();
        return new ResponseEntity<>(new MockResponseGeneric("Shutdown application with exit code " + exitCode + ", bye..."), HttpStatus.OK);
    }

}
