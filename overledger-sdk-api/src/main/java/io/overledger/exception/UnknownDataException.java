package io.overledger.exception;

/**
 * Throw this exception when transaction does not have Quant data
 */
public class UnknownDataException extends Exception {

    public UnknownDataException() {
        super("No Quant data found");
    }

}
