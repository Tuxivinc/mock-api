package org.api.mock.model;

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
public class CallInfo {
    private String hostName;
    private String hostIp;
    private String url;
    private String uri;
    private String query;
    private Map<String, List<String>> headers;

    private String body;

    /**
     * Instantiates a new Call info.
     *
     * @param headers the headers
     * @param request the request
     */
    public CallInfo(HttpHeaders headers, HttpServletRequest request) {
        this.headers = headers.entrySet().stream().collect(Collectors.toMap(o -> o.getKey(), t -> t.getValue()));
        this.query = request.getQueryString();
        this.url = request.getRequestURL().toString();
        this.uri = request.getRequestURI();
        try {
            this.hostIp = InetAddress.getLocalHost().getHostAddress();
            this.hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            this.hostName = "Error";
            this.hostIp = "Error";
            e.printStackTrace();
        }

        try (BufferedReader reader = request.getReader()) {
            if (Objects.nonNull(reader) && Objects.nonNull(reader.lines())) {
                this.body = reader.lines().collect(Collectors.joining(""));
            }
        } catch (java.io.IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Getter for property 'hostName'.
     *
     * @return Value for property 'hostName'.
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * Setter for property 'hostName'.
     *
     * @param hostName Value to set for property 'hostName'.
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * Getter for property 'hostIp'.
     *
     * @return Value for property 'hostIp'.
     */
    public String getHostIp() {
        return hostIp;
    }

    /**
     * Setter for property 'hostIp'.
     *
     * @param hostIp Value to set for property 'hostIp'.
     */
    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    /**
     * Getter for property 'url'.
     *
     * @return Value for property 'url'.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Setter for property 'url'.
     *
     * @param url Value to set for property 'url'.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Getter for property 'uri'.
     *
     * @return Value for property 'uri'.
     */
    public String getUri() {
        return uri;
    }

    /**
     * Setter for property 'uri'.
     *
     * @param uri Value to set for property 'uri'.
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * Getter for property 'query'.
     *
     * @return Value for property 'query'.
     */
    public String getQuery() {
        return query;
    }

    /**
     * Setter for property 'query'.
     *
     * @param query Value to set for property 'query'.
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * Getter for property 'headers'.
     *
     * @return Value for property 'headers'.
     */
    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    /**
     * Setter for property 'headers'.
     *
     * @param headers Value to set for property 'headers'.
     */
    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    /**
     * Gets body.
     *
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets body.
     *
     * @param body the body
     */
    public void setBody(String body) {
        this.body = body;
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
