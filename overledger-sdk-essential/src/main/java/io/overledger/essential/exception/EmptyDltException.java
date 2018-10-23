package io.overledger.essential.exception;

/**
 * Throw this exception for DLT not supported
 */
public class EmptyDltException extends Exception {

    public EmptyDltException() {
        super("No DLT found");
    }

}
