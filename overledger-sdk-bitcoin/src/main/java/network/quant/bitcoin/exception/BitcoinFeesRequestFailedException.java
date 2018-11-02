package network.quant.bitcoin.exception;

/**
 * Throw this exception when Bitcoin fee service failed
 */
public class BitcoinFeesRequestFailedException extends Exception {

    public BitcoinFeesRequestFailedException(String message) {
        super(String.format("Unable to retrieve fee from bitcoinfees.earn.com: %s", message));
    }

}
