package network.quant.essential.exception;

/**
 * Throw this exception for DLT not supported
 */
public class DltNotSupportedException extends Exception {

    public DltNotSupportedException() {
        super("DLT type is not supported");
    }

}
