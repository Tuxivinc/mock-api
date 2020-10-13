package org.api.mock.ctrl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.api.mock.model.MockResponseGeneric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * The type Api get informations.
 */
@RestController
@Tag(name = "Info", description = "System information")
@RequestMapping("/getinfos")
public class ApiMockInfos {

    private static final Logger LOG = LoggerFactory.getLogger(ApiMockInfos.class);

    /**
     * Get jvm args
     *
     * @return
     */
    @GetMapping(value = "/jvm")
    @Operation(summary = "Return informations arguments of JVM")
    public ResponseEntity<Map<String, String>> getJvmInformation() {
        return new ResponseEntity<>(
                System.getProperties().stringPropertyNames()
                        .stream()
                        .collect(
                                Collectors.toMap(
                                        entry -> entry,
                                        System::getProperty
                                )),
                HttpStatus.OK);
    }

    /**
     * Get Hostname
     *
     * @return
     */
    @GetMapping(value = "/hostname")
    @Operation(summary = "Return hostname")
    public ResponseEntity<MockResponseGeneric> getHostname() {
        String hostName;
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            LOG.error("Error when retry hostname", e);
            hostName = "error";
        }
        return new ResponseEntity<>(
                new MockResponseGeneric(hostName),
                HttpStatus.OK);
    }

    /**
     * Get environnement variables
     *
     * @return
     */
    @GetMapping(value = "/varenv")
    @Operation(summary = "Return all environment variable")
    public ResponseEntity<Map<String, String>> getEnvVar() {
        return new ResponseEntity<>(
                System.getenv(),
                HttpStatus.OK);
    }

}
