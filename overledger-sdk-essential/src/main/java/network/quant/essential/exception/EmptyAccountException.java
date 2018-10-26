package network.quant.essential.exception;

/**
 * Throw this exception for DLT not supported
 */
public class EmptyAccountException extends Exception {

    public EmptyAccountException() {
        super("DLT account is not found");
    }

}
