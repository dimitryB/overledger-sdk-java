package io.overledger.exception;

/**
 * Throw this exception when function call is not matching the network address
 */
public class NetworkNotMatchException extends Exception {

    public NetworkNotMatchException() {
        super("Cannot match the network address");
    }

}
