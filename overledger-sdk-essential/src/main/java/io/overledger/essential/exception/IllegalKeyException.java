package io.overledger.essential.exception;

/**
 * Throw this exception for DLT not supported
 */
public class IllegalKeyException extends Exception {

    public IllegalKeyException() {
        super("DLT type is not found");
    }

}
