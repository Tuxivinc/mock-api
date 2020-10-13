package org.api.mock.model;

import lombok.Data;

/**
 * The type Mock response generic format.
 */
@Data
public class MockResponseGeneric {

    private String message;

    public MockResponseGeneric(String message) {
        this.message = message;
    }
}
