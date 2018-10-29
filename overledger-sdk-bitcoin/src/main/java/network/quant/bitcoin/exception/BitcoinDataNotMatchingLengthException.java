package network.quant.bitcoin.exception;

/**
 * Throw this exception when Bitcoin fee service failed
 */
public class BitcoinDataNotMatchingLengthException extends Exception {

    public BitcoinDataNotMatchingLengthException() {
        super(String.format("Cannot extract data from address list with given length"));
    }

}
