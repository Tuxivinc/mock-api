package org.api.mock.model;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Information when call mock (url, headers, body).
 */
@Data
public class CallInfo {
    private String hostName;
    private String hostIp;
    private String url;
    private String uri;
    private String query;
    private Map<String, List<String>> headers;

    private String body;

    private static final Logger LOG = LoggerFactory.getLogger(CallInfo.class);

    /**
     * Instantiates a new Call info.
     *
     * @param headers the headers
     * @param request the request
     */
    public CallInfo(HttpHeaders headers, HttpServletRequest request) {
        this.headers = headers.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        this.query = request.getQueryString();
        this.url = request.getRequestURL().toString();
        this.uri = request.getRequestURI();
        try {
            this.hostIp = InetAddress.getLocalHost().getHostAddress();
            this.hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            this.hostName = "Error";
            this.hostIp = "Error";
            // TODO pas de métier dans le model !!
            LOG.error("Cannot obtains Inet informations", e);
        }

        try (BufferedReader reader = request.getReader()) {
            if (Objects.nonNull(reader) && Objects.nonNull(reader.lines())) {
                this.body = reader.lines().collect(Collectors.joining(""));
            }
        } catch (java.io.IOException ex) {
            // TODO pas de métier dans le model !!
            LOG.error("Error optains Reader of request", ex);
        }
    }

    @Override
    public String toString() {
        return "CallInfo{" +
                "hostName='" + hostName + '\'' +
                ", hostIp='" + hostIp + '\'' +
                ", url='" + url + '\'' +
                ", uri='" + uri + '\'' +
                ", query='" + query + '\'' +
                ", headers=" + headers +
                ", body='" + body + '\'' +
                '}';
    }
}
