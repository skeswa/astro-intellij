package io.astrolib.jvx.psi.error;

/**
 * Created by skeswa on 5/30/16.
 */
public enum JVXErrorMessage {
    EXPECTED_GT("Expected \">\""),
    INVALID_TAG_TYPE("Invalid tag type"),
    EXPECTED_STR_OR_EXPR("Expected a string or braced expression");

    private final String message;

    private JVXErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
