package org.api.mock.ctrl;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@RequestMapping("/getinfos")
public class ApiMockInfos {

    private static final Logger LOG = LoggerFactory.getLogger(ApiMockInfos.class);

    /**
     * Get jvm args
     *
     * @return
     */
    @GetMapping(value = "/jvm")
    @ApiOperation(value = "getJvmInformation", nickname = "Return informations arguments of JVM")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure", response = String.class)})
    public ResponseEntity<Map<String, String>> getJvmInformation() {
        return new ResponseEntity<>(
                System.getProperties().stringPropertyNames()
                        .stream()
                        .collect(
                                Collectors.toMap(
                                        entry -> entry,
                                        entry -> System.getProperty(entry)
                                )),
                HttpStatus.OK);
    }

    /**
     * Get Hostname
     *
     * @return
     */
    @GetMapping(value = "/hostname")
    @ApiOperation(value = "getHostname", nickname = "Return hostname")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure", response = String.class)})
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
    @ApiOperation(value = "getEnvVar", nickname = "Return all environment variable")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure", response = String.class)})
    public ResponseEntity<Map<String, String>> getEnvVar() {
        return new ResponseEntity<>(
                System.getenv(),
                HttpStatus.OK);
    }

}
