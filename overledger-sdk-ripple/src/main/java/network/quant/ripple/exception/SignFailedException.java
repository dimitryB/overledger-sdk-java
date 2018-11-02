package network.quant.ripple.exception;

/**
 * Throw this exception when Ethereum gas service failed
 */
public class SignFailedException extends Exception {

    public SignFailedException(String message) {
        super(String.format("Failed to sign the transaction: %s", message));
    }

}
