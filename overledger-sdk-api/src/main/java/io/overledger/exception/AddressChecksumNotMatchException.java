package io.overledger.exception;

/**
 * Throw this exception when address checksum can not match the address calculation
 */
public class AddressChecksumNotMatchException extends Exception {

    public AddressChecksumNotMatchException() {
        super("Given address does not match the checksum");
    }

}
