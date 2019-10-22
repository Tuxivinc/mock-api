package org.api.mock.ctrl;

import io.swagger.annotations.*;
import org.api.mock.model.MockResponseGeneric;
import org.api.mock.model.SessionResponse;
import org.api.mock.services.CacheSession;
import org.api.mock.services.SimulLocalSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * The type Api mock Session.
 */
@RestController
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
    @ApiOperation(value = "getSessionLocal", nickname = "Get session link onto token in local instance without share session")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "Token session", required = true, paramType = "path", defaultValue = "4gfe5hr48")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = MockResponseGeneric.class),
            @ApiResponse(code = 201, message = "Session created", response = MockResponseGeneric.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure", response = String.class)})
    public ResponseEntity<MockResponseGeneric> getSessionLocal(@PathVariable String token) {
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
    @ApiOperation(value = "getSessionMulticast", nickname = "Get session link onto token with ehcache in multicast")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "Token session", required = true, paramType = "path", defaultValue = "4gfe5hr49")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = MockResponseGeneric.class),
            @ApiResponse(code = 201, message = "Session created", response = MockResponseGeneric.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure", response = String.class)})
    public ResponseEntity<MockResponseGeneric> getSessionMulticast(@PathVariable String token) {
        SessionResponse response = cacheSession.getSession(token);
        return new ResponseEntity<>(new MockResponseGeneric(response.getValue()), response.isExist() ? HttpStatus.OK : HttpStatus.CREATED);
    }

}
