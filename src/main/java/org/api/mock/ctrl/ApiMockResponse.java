package org.api.mock.ctrl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.api.mock.services.FileResponseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.io.IOException;

/**
 * The type Api mock Response.
 */
@RestController
@Tag(name = "Mock", description = "Get json file in /mock-response file")
@RequestMapping("/mock")
public class ApiMockResponse {

    private static final Logger LOG = LoggerFactory.getLogger(ApiMockResponse.class);

    @Resource
    private FileResponseService fileResponseService;

    /**
     * Gets file
     *
     * @param filename the filename
     * @return the headers
     */
    @GetMapping(value = "/{filename}", produces = "application/json")
    @Operation(summary = "get Json file")
    public ResponseEntity<String> getContentFile(@NotBlank @PathVariable String filename) {
        try {
            return fileResponseService.getContentFile(filename)
                    .map(r -> new ResponseEntity<>(r, HttpStatus.OK))
                    .orElse(getError(String.format("{\"Error\":\"No file %s found\"}", filename)));
        } catch (IOException e) {
            LOG.error("Enable to read file {}", filename, e);
            return getError(String.format("{\"Error\":\"%s\"}", e.getMessage()));
        }
    }

    private ResponseEntity<String> getError(String message) {
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/{filename}", produces = "application/json")
    @Operation(summary = "add file response")
    public ResponseEntity<String> setContentFile(@NotBlank @PathVariable String filename, @RequestBody @NotBlank String content) {
        try {
            return new ResponseEntity<>(fileResponseService.addFile(filename, content), HttpStatus.OK);
        } catch (IOException e) {
            LOG.error("Enable to create file {}", filename, e);
            return getError(String.format("{\"Error\":\"%s -- %s\"}", e.getClass().getSimpleName(), e.getMessage()));
        }
    }

}
