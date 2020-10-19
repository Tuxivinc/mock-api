package org.api.mock.ctrl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.api.mock.model.CallInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.net.InetAddress.getLocalHost;

/**
 * The type Api mock sniffer.
 */
@RestController
@Tag(name = "Sniffer", description = "Get Request Informations")
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
    @RequestMapping(value = "/headers", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS, RequestMethod.HEAD, RequestMethod.PUT}, produces = "application/json")
    @Operation(summary = "get header informations")
    public CallInfo getHeaders(@RequestHeader HttpHeaders headers, HttpServletRequest request) {
        InetAddress netInfo = null;
        try {
            netInfo = getLocalHost();
        } catch (UnknownHostException e) {
            LOG.error("Cannot obtains Inet informations", e);
        }
        CallInfo.CallInfoBuilder callInfoBuilder = CallInfo.CallInfoBuilder.aCallInfo()
                .withHeaders(headers.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)))
                .withQuery(request.getQueryString())
                .withUrl(request.getRequestURL().toString())
                .withUri(request.getRequestURI())
                .withHostIp(Optional.ofNullable(netInfo).map(InetAddress::getHostAddress).orElse("Error"))
                .withHostName(Optional.ofNullable(netInfo).map(InetAddress::getHostName).orElse("Error"));

        try (BufferedReader reader = request.getReader()) {
            if (Objects.nonNull(reader) && Objects.nonNull(reader.lines())) {
                callInfoBuilder.withBody(reader.lines().collect(Collectors.joining("")));
            }
        } catch (java.io.IOException ex) {
            LOG.error("Error optains Reader of request", ex);
        }
        return callInfoBuilder.build();
    }

}
