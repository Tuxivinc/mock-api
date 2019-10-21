package org.api.mock.ctrl;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.api.mock.model.CallInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Api mock sniffer.
 */
@RestController
@RequestMapping("/sniffer")
public class ApiMockSniffer {
    private static final Logger LOG = LoggerFactory.getLogger(ApiMockSniffer.class);

    /**
     * Gets headers, url and body informations
     *
     * @param headers the headers
     * @param request the request
     * @return the headers
     */
    @RequestMapping(value = "/headers", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PATCH}, produces = "application/json")
    @ApiOperation(value = "getHeaders", nickname = "get header informations")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = CallInfo.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public CallInfo getHeaders(@RequestHeader HttpHeaders headers, HttpServletRequest request) {
        CallInfo callInfo = new CallInfo(headers, request);
        LOG.debug("{}", callInfo);
        return callInfo;

    }

}
