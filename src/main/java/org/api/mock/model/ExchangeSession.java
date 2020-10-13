package org.api.mock.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ExchangeSession implements Serializable {
    private String value;
    private String token;

    public ExchangeSession() {
    }

    public ExchangeSession(String value, String token) {
        this.value = value;
        this.token = token;
    }
}