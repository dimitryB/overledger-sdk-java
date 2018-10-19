package io.overledger.exception;

/**
 * Throw this exception if transaction data is over the size
 */
public class DataOverSizeException extends Exception {

    public DataOverSizeException() {
        super("Given data is over sized");
    }

}
