package network.quant.ethereum.exception;

/**
 * Throw this exception when Ethereum gas service failed
 */
public class EthGasStationRequestFailedException extends Exception {

    public EthGasStationRequestFailedException(String message) {
        super(String.format("Unable to retrieve fee from ethgasstation.info: %s", message));
    }

}
