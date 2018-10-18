package io.overledger.exception;

/**
 * Throw this exception when no Quant message can be found in transaction
 */
public class DataNotFoundException extends Exception {

    public DataNotFoundException() {
        super("No Quant data can be found");
    }

}
