package org.api.mock.model;

/**
 * The type Mock response generic format.
 */
public class MockResponseGeneric {

    private String message;

    /**
     * Instantiates a new Mock response generic.
     *
     * @param message the message
     */
    public MockResponseGeneric(String message) {
        this.message = message;
    }

    /**
     * Getter for property 'message'.
     *
     * @return Value for property 'message'.
     */
    public String getMessage() {
        return message;
    }
}
