package org.api.mock.ctrl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.api.mock.model.MockResponseGeneric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * The type Api shutdown (control exit application).
 */
@RestController
@Tag(name = "Shutdown", description = "Shutdown App with exit code")
@RequestMapping("/shutdown")
public class ApiShutdown {

    private static final Logger LOG = LoggerFactory.getLogger(ApiShutdown.class);

    /**
     * Shutdown response entity.
     *
     * @return the response entity
     */
    @PostMapping(value = "/{exitCode}", produces = "application/json")
    @Operation(summary = "close jvm")
    public ResponseEntity<MockResponseGeneric> shutdown(@NotBlank @DefaultValue("0") @PathVariable int exitCode) {
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
