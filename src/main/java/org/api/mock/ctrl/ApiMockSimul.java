package org.api.mock.ctrl;

import io.swagger.annotations.*;
import org.api.mock.model.MockResponseGeneric;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * The type Api mock simul.
 */
@RestController
@RequestMapping("/simulation")
public class ApiMockSimul {


    /**
     * The constant PREFIX_FILE_TMP.
     */
    public static final String PREFIX_FILE_TMP = "MOCK_API";
    /**
     * The constant SUFFIX_FILE_TMP.
     */
    public static final String SUFFIX_FILE_TMP = ".txt";

    /**
     * Gets simul traitement.
     *
     * @param sleepInMs the sleep in ms
     * @return the simul traitement
     * @throws InterruptedException the interrupted exception
     */
    @GetMapping(value = "/timeout/{sleepInMs}", produces = "application/json")
    @ApiOperation(value = "getSimulTraitement", nickname = "Wait time in ms for simul traitements")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sleepInMs", value = "Sleep in Ms", required = true, dataType = "long", paramType = "path", defaultValue = "5000")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Value not long type", response = String.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure", response = String.class)})
    public ResponseEntity<MockResponseGeneric> getSimulTraitement(@PathVariable String sleepInMs) throws InterruptedException {
        // Vérification param
        if (!sleepInMs.matches("\\d{1,19}")) {
            throw new NumberFormatException("Value \"sleepInMs\" not Long type: " + sleepInMs);
        }
        // Wait
        Thread.sleep(Long.valueOf(sleepInMs));
        return new ResponseEntity<>(new MockResponseGeneric(String.format("Sleep %sms -> OK", sleepInMs)), HttpStatus.OK);
    }

    /**
     * Gets simul response size.
     *
     * @param sizeInKo the size in ko
     * @return the simul response size
     * @throws IOException the io exception
     */
    @GetMapping(value = "/response-size/{sizeInKo}")
    @ApiOperation(value = "getSimulResponseSize", nickname = "Response file size")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sizeInKo", value = "Size file response in ko", required = true, dataType = "long", paramType = "path", defaultValue = "5000")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = FileSystemResource.class),
            @ApiResponse(code = 400, message = "Value not long type", response = String.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure", response = String.class)})
    public ResponseEntity<FileSystemResource> getSimulResponseSize(@PathVariable String sizeInKo) throws IOException {
        // Vérification param
        if (!sizeInKo.matches("\\d{1,19}")) {
            throw new NumberFormatException("Value \"sizeInKo\" not Long type: " + sizeInKo);
        }

        File f = File.createTempFile(PREFIX_FILE_TMP, SUFFIX_FILE_TMP);
        f.deleteOnExit();
        try (FileOutputStream outputStream = new FileOutputStream(f)) {

            byte[] buf = new byte[Integer.valueOf(sizeInKo) * 1024];
            outputStream.write(buf);
            outputStream.flush();
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.setContentLength(f.length());
            headers.set("content-disposition", "attachment; filename=" + f.getName());
            return new ResponseEntity<>(new FileSystemResource(f), headers, HttpStatus.CREATED);
        }
    }

    /**
     * Gets simul code error.
     *
     * @param httpCode the http code
     * @return the simul error
     */
    @GetMapping(value = "/error/{httpCode}")
    @ApiOperation(value = "getSimulError", nickname = "Error reponse")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "httpCode", value = "HTTP Code error", required = true, dataType = "long", paramType = "path", defaultValue = "404")
    })
    public ResponseEntity<MockResponseGeneric> getSimulError(@PathVariable String httpCode) {
        // Vérification param
        if (!httpCode.matches("\\d{1,10}")) {
            throw new NumberFormatException("Value \"codeError\" not Int type: " + httpCode);
        }

        HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(httpCode));
        return new ResponseEntity<>(new MockResponseGeneric(httpStatus.getReasonPhrase()), httpStatus);
    }

}
