package org.api.mock.ctrl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The type Api mock Response.
 */
@RestController
@Tag(name = "Mock", description = "Get json file in /mock-response file")
@RequestMapping("/mock")
public class ApiMockResponse {
    private static final Logger LOG = LoggerFactory.getLogger(ApiMockResponse.class);

    @Value("${path.mock.response}")
    private String PathMockResponse;

    /**
     * Gets file
     *
     * @param filename the filename
     * @return the headers
     */
    @RequestMapping(value = "/{filename}", method = {RequestMethod.GET}, produces = "application/json")
    @Operation(summary = "get header informations")
    public ResponseEntity<String> getContentFile(@NotBlank @PathVariable String filename) throws IOException {
        LOG.debug("Get File {}/{}", PathMockResponse, filename);
        return Files.list(Path.of(PathMockResponse))
                .filter(f -> !f.toFile().isDirectory())
                .filter(f -> f.toFile().getName().equals(filename))
                .map(file -> {
                    try {
                        return Files.readString(Paths.get(file.toUri()));
                    } catch (IOException e) {
                        LOG.error("Cannot read file {}", file.getFileName());
                        return null;
                    }
                })
                .findFirst()
                .map(r -> new ResponseEntity<>(r, HttpStatus.OK))
                .orElse(new ResponseEntity<>(String.format("{\"Error\":\"No file %s/%s found\"}", PathMockResponse, filename), HttpStatus.NOT_FOUND));
    }

}
