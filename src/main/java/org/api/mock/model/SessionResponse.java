package org.api.mock.model;

import lombok.Data;

import java.net.UnknownHostException;

import static java.net.InetAddress.getLocalHost;

@Data
public class SessionResponse {
    private boolean exist;
    private String value;
    private String host;

    public SessionResponse(boolean exist, String value) {
        this.exist = exist;
        this.value = value;
        try {
            this.host = getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            this.host = "Undefined";
        }
    }

    @Override
    public String toString() {
        return "SessionResponse{" +
                "exist=" + exist +
                ", value='" + value + '\'' +
                ", host='" + host + '\'' +
                '}';
    }
}