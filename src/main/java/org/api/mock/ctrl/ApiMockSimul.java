package org.api.mock.ctrl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.api.mock.model.MockResponseGeneric;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * The type Api mock simul.
 */
@RestController
@RequestMapping("/simulation")
@Tag(name = "Simulation", description = "Simulate system comportment")
public class ApiMockSimul {


    /**
     * The constant PREFIX_FILE_TMP.
     */
    public static final String PREFIX_FILE_TMP = "MOCK_API";
    /**
     * The constant SUFFIX_FILE_TMP.
     */
    public static final String SUFFIX_FILE_TMP = ".txt";

    private long sleepInMsMemory = 0l;

    /**
     * Gets simul traitement.
     *
     * @param sleepInMs the sleep in ms
     * @return the simul traitement
     * @throws InterruptedException the interrupted exception
     */
    @GetMapping(value = "/timeout/{sleepInMs}", produces = "application/json")
    @Operation(summary = "Wait time in ms for simul slow work")
    public ResponseEntity<MockResponseGeneric> getSimulTraitement(@NotBlank @DefaultValue("5000") @PathVariable String sleepInMs) throws InterruptedException {
        // Vérification param
        if (!sleepInMs.matches("\\d{1,19}")) {
            throw new NumberFormatException("Value \"sleepInMs\" not Long type: " + sleepInMs);
        }
        // Wait
        Thread.sleep(Long.valueOf(sleepInMs));
        return new ResponseEntity<>(new MockResponseGeneric(String.format("Sleep %sms -> OK", sleepInMs)), HttpStatus.OK);
    }

    /**
     * Gets simul traitement (in memory).
     *
     * @return the simul traitement
     * @throws InterruptedException the interrupted exception
     */
    @GetMapping(value = "/timeout-memory", produces = "application/json")
    @Operation(summary = "Wait time in ms for simul traitements (by memory)")
    public ResponseEntity<MockResponseGeneric> getSimulTraitementMemory() throws InterruptedException {
        // Wait
        Thread.sleep(sleepInMsMemory);
        return new ResponseEntity<>(new MockResponseGeneric(String.format("Sleep %sms -> OK", sleepInMsMemory)), HttpStatus.OK);
    }

    /**
     * Set simul ms traitement in memory
     *
     * @throws InterruptedException the interrupted exception
     */
    @PostMapping(value = "/timeout-memory/{sleepInMs}", produces = "application/json")
    @Operation(summary = "Set in memory time waiting")
    public ResponseEntity<MockResponseGeneric> setSimulTraitementMemory(@NotBlank @DefaultValue("5000") @PathVariable String sleepInMs) {
        sleepInMsMemory = Long.valueOf(sleepInMs);
        return new ResponseEntity<>(new MockResponseGeneric(String.format("Set Sleep timeout in memory %sms -> OK", sleepInMsMemory)), HttpStatus.OK);
    }

    /**
     * Gets simul response size.
     *
     * @param sizeInKo the size in ko
     * @return the simul response size
     * @throws IOException the io exception
     */
    @GetMapping(value = "/response-size/{sizeInKo}")
    @Operation(summary = "Response file size")
    public ResponseEntity<FileSystemResource> getSimulResponseSize(@NotBlank @DefaultValue("5000") @PathVariable String sizeInKo) throws IOException {
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
    @Operation(summary = "Error reponse")
    public ResponseEntity<MockResponseGeneric> getSimulError(@NotBlank @DefaultValue("404") @PathVariable String httpCode) {
        // Vérification param
        if (!httpCode.matches("\\d{1,10}")) {
            throw new NumberFormatException("Value \"codeError\" not Int type: " + httpCode);
        }

        HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(httpCode));
        return new ResponseEntity<>(new MockResponseGeneric(httpStatus.getReasonPhrase()), httpStatus);
    }

}
