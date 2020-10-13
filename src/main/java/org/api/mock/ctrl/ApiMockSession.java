package org.api.mock.ctrl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.api.mock.model.MockResponseGeneric;
import org.api.mock.model.SessionResponse;
import org.api.mock.services.CacheSession;
import org.api.mock.services.SimulLocalSession;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;


/**
 * The type Api mock Session.
 */
@RestController
@Tag(name = "Session", description = "Simulation sessions")
@RequestMapping("/session")
public class ApiMockSession {

    @Resource
    private SimulLocalSession simulLocalSession;

    @Resource
    private CacheSession cacheSession;

    /**
     * Gets session store on server.
     *
     * @param token token of session
     * @return Date created session
     */
    @GetMapping(value = "/notshare/{token}", produces = "application/json")
    @Operation(summary = "Get session link onto token in local instance without share session")
    public ResponseEntity<MockResponseGeneric> getSessionLocal(@NotBlank @PathVariable String token) {
        SessionResponse response = simulLocalSession.getSession(token);
        return new ResponseEntity<>(new MockResponseGeneric(response.getValue()), response.isExist() ? HttpStatus.OK : HttpStatus.CREATED);
    }

    /**
     * Gets session using ehcache in multicast
     *
     * @param token token of session
     * @return Date created session
     */
    @GetMapping(value = "/share/{token}", produces = "application/json")
    @Operation(summary = "Get session link onto token with ehcache in multicast")
    public ResponseEntity<MockResponseGeneric> getSessionMulticast(@NotBlank @DefaultValue("4gfe5hr49") @PathVariable String token) {
        SessionResponse response = cacheSession.getSession(token);
        return new ResponseEntity<>(new MockResponseGeneric(response.getValue()), response.isExist() ? HttpStatus.OK : HttpStatus.CREATED);
    }

}
