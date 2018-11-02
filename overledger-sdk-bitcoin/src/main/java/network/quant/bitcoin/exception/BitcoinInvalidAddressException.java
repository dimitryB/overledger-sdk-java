package network.quant.bitcoin.exception;

/**
 * Throw this exception when Bitcoin fee service failed
 */
public class BitcoinInvalidAddressException extends Exception {

    public BitcoinInvalidAddressException(String address) {
        super(String.format("Unable to verify address: %s", address));
    }

}
