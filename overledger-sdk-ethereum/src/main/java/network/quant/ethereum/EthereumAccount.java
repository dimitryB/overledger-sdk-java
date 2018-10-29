package network.quant.ethereum;

import network.quant.api.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.web3j.crypto.*;
import org.web3j.utils.Numeric;
import javax.xml.bind.DatatypeConverter;
import java.math.BigInteger;
import java.util.Optional;

/**
 * Ethereum implementation of Account
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class EthereumAccount implements Account {

    private static final BigInteger DEFAULT_MAIN_GAS_LIMIT = new BigInteger("8000000");
    private static final BigInteger DEFAULT_TEST_GAS_LIMIT = new BigInteger("4712388");
    private static EthereumAccount I;
    NETWORK network;
    ECKeyPair ecKeyPair;
    BigInteger nonce;
    BigInteger gasLimit;
    Encryptor encryptor;
    Compressor compressor;

    private EthereumAccount(NETWORK network) throws Exception {
        this(network, Keys.createEcKeyPair(), new BigInteger("0"));
    }

    private EthereumAccount(NETWORK network, BigInteger privateKey, BigInteger nonce) {
        this(network, ECKeyPair.create(privateKey), nonce);
    }

    private EthereumAccount(NETWORK network, byte[] privateKey, BigInteger nonce) {
        this(network, ECKeyPair.create(privateKey), nonce);
    }

    private EthereumAccount(NETWORK network, ECKeyPair privateKey, BigInteger nonce) {
        this.network = network;
        this.ecKeyPair = privateKey;
        this.nonce = nonce;
        this.gasLimit = NETWORK.MAIN.equals(this.network) ? DEFAULT_MAIN_GAS_LIMIT : DEFAULT_TEST_GAS_LIMIT;
    }

    private void sign(String toAddress, String message, DltTransactionRequest dltTransaction) {
        RawTransaction rawTransaction = RawTransaction.createTransaction(
                this.nonce = null != dltTransaction.getSequence() ? BigInteger.valueOf(dltTransaction.getSequence()) : this.nonce,
                Optional.ofNullable(dltTransaction.getFee()).orElse(EthGasStation.getInstance().calculate(FEE_POLICY.NORMAL)),
                Optional.ofNullable(dltTransaction.getFeeLimit()).orElse(this.gasLimit),
                toAddress,
                dltTransaction.getAmount(),
                message
        );
        byte transactionSignedBytes[] = TransactionEncoder.signMessage(rawTransaction, Credentials.create(this.ecKeyPair));
        dltTransaction.setSignedTransaction(Numeric.toHexString(transactionSignedBytes));
        this.nonce = this.nonce.add(BigInteger.ONE);
    }

    @Override
    public Account withNetwork(NETWORK network) {
        this.network = network;
        return this;
    }

    @Override
    public void setPrivateKey(BigInteger key) {
        this.ecKeyPair = ECKeyPair.create(key);
    }

    @Override
    public BigInteger getPrivateKey() {
        return this.ecKeyPair.getPrivateKey();
    }

    @Override
    public void sign(String fromAddress, String toAddress, String message, DltTransaction dltTransaction) {
        if (dltTransaction instanceof DltTransactionRequest) {
            byte data[] = message.getBytes();
            if (null == this.encryptor) {
                data = this.encryptor.encrypt(data);
                message = DatatypeConverter.printHexBinary(data);
            }
            if (null == this.compressor) {
                data = this.compressor.compress(data);
                message = DatatypeConverter.printHexBinary(data);
            }
            this.sign(toAddress, message, (DltTransactionRequest)dltTransaction);
        }
    }

    @Override
    public void sign(String fromAddress, String toAddress, byte[] data, DltTransaction dltTransaction) {
        if (dltTransaction instanceof DltTransactionRequest) {
            String message = DatatypeConverter.printHexBinary(data);
            if (null == this.encryptor) {
                data = this.encryptor.encrypt(data);
                message = DatatypeConverter.printHexBinary(data);
            }
            if (null == this.compressor) {
                data = this.compressor.compress(data);
                message = DatatypeConverter.printHexBinary(data);
            }
            this.sign(toAddress, message, (DltTransactionRequest)dltTransaction);
        }
    }

    /**
     * Create Ethereum wallet instance with new key
     * @param network NETWORK containing network param
     * @param encryptor Encryptor containing encryptor
     * @param compressor Compressor containing compressor
     * @return Account containing a Ethereum wallet
     */
    public static Account getInstance(NETWORK network, Encryptor encryptor, Compressor compressor) throws Exception {
        I = new EthereumAccount(network);
        I.encryptor = encryptor;
        I.compressor = compressor;
        return I;
    }

    /**
     * Get Ethereum wallet instance by given secret key number
     * @param network NETWORK containing network param
     * @param privateKey BigInteger containing the key
     * @param encryptor Encryptor containing encryptor
     * @param compressor Compressor containing compressor
     * @return Account containing a Ethereum wallet
     */
    public static Account getInstance(NETWORK network, BigInteger privateKey, BigInteger nonce, Encryptor encryptor, Compressor compressor) {
        I = new EthereumAccount(network, privateKey, nonce);
        I.encryptor = encryptor;
        I.compressor = compressor;
        return I;
    }

    /**
     * Get Ethereum wallet instance by given secret key array
     * @param network NETWORK containing network param
     * @param privateKey byte array containing the key in array
     * @param encryptor Encryptor containing encryptor
     * @param compressor Compressor containing compressor
     * @return Account containing a Ethereum wallet
     */
    public static Account getInstance(NETWORK network, byte privateKey[], BigInteger nonce, Encryptor encryptor, Compressor compressor) {
        I = new EthereumAccount(network, privateKey, nonce);
        I.encryptor = encryptor;
        I.compressor = compressor;
        return I;
    }

    /**
     * Get Ethereum wallet instance by given secret key object
     * @param network NETWORK containing network param
     * @param privateKey ECKey containing the key
     * @param encryptor Encryptor containing encryptor
     * @param compressor Compressor containing compressor
     * @return Account containing a Ethereum wallet
     */
    public static Account getInstance(NETWORK network, ECKeyPair privateKey, BigInteger nonce, Encryptor encryptor, Compressor compressor) {
        I = new EthereumAccount(network, privateKey, nonce);
        I.encryptor = encryptor;
        I.compressor = compressor;
        return I;
    }

}
