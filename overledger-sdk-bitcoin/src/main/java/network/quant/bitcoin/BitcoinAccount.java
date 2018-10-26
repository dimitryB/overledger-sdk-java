package network.quant.bitcoin;

import network.quant.api.*;
import network.quant.exception.DataOverSizeException;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.*;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.script.Script;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Bitcoin implementation of Account
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class BitcoinAccount implements Account {

    private static BitcoinAccount I;
    private static final int MAIN_ADDR = 0x00;
    private static final int TEST_ADDR = 0x6F;
    private static final int NAME_ADDR = 0x34;
    private static final String SCRIPT_OP_DUP_OP_HASH160_PUBKEY_OP_EQUALVERIFY_OP_CHECKSIG = "76a914%s88ac";
    NetworkParameters networkParameters;
    ECKey key;
    List<UTXO> utxoList;
    Encryptor encryptor;
    Compressor compressor;

    private BitcoinAccount(NetworkParameters networkParameters) {
        this(networkParameters, new ECKey());
    }

    private BitcoinAccount(NetworkParameters networkParameters, BigInteger privateKey) {
        this(networkParameters, ECKey.fromPrivate(privateKey));
    }

    private BitcoinAccount(NetworkParameters networkParameters, byte privateKey[]) {
        this(networkParameters, ECKey.fromPrivate(privateKey));
    }

    private BitcoinAccount(NetworkParameters networkParameters, ECKey privateKey) {
        Context.getOrCreate(networkParameters);
        this.networkParameters = networkParameters;
        this.key = privateKey;
    }

    private void sign(String fromAddress, String toAddress, byte[] message, DltTransactionRequest dltTransaction) {
        Transaction transaction = new Transaction(this.networkParameters);
        Coin totalPayout = Coin.valueOf(dltTransaction.getAmount().longValue());
        int outputNumber = 2;
        if ((null != dltTransaction.getMessage() && !dltTransaction.getMessage().isEmpty())) {
            BitcoinData bitcoinData;
            try {
                switch (this.networkParameters.getAddressHeader()) {
                    case MAIN_ADDR:
                        bitcoinData = new BitcoinData(NETWORK.MAIN, message, DATA_TYPE.TEXT, this.encryptor, this.compressor);
                        break;
                    case TEST_ADDR:
                        bitcoinData = new BitcoinData(NETWORK.TEST, message, DATA_TYPE.TEXT, this.encryptor, this.compressor);
                        break;
                    default:
                        bitcoinData = new BitcoinData(NETWORK.TEST, message, DATA_TYPE.TEXT, this.encryptor, this.compressor);
                        break;
                }
                for (String address : bitcoinData.getAddressList()) {
                    transaction.addOutput(Transaction.MIN_NONDUST_OUTPUT, Address.fromBase58(this.networkParameters, address));
                    totalPayout = totalPayout.add(Transaction.MIN_NONDUST_OUTPUT);
                }
                outputNumber += bitcoinData.getAddressList().size();
            } catch (DataOverSizeException e) {
                log.error(this.getClass().getSimpleName()+"#signTransaction()", e);
            }
        }
        transaction.addOutput(Coin.valueOf(dltTransaction.getAmount().longValue()), Address.fromBase58(this.networkParameters, toAddress));
        totalPayout = totalPayout.add(Coin.valueOf(
                (null == dltTransaction.getFee()) ?
                        BitcoinFees.getInstance().calculate(FEE_POLICY.NORMAL, 1, outputNumber).longValue() :
                        dltTransaction.getFee().longValue()
        ));
        final Coin payout = Coin.valueOf(totalPayout.getValue());
        UTXO inputUtxo = this.utxoList.stream()
                .filter(utxo -> utxo.getAddress().equals(fromAddress) && utxo.getValue().isGreaterThan(payout))
                .findFirst().orElse(null);
        if (null == inputUtxo) {
            return;
        }
        transaction.addOutput(inputUtxo.getValue().subtract(totalPayout), Address.fromBase58(this.networkParameters,
                (null == dltTransaction.getChangeAddress()) ? fromAddress : dltTransaction.getChangeAddress()));
        TransactionOutPoint transactionOutPoint = new TransactionOutPoint(this.networkParameters, inputUtxo.getIndex(), inputUtxo.getHash());
        transaction.addSignedInput(transactionOutPoint, inputUtxo.getScript(), this.key, Transaction.SigHash.ALL, true);
        transaction.getConfidence().setSource(TransactionConfidence.Source.SELF);
        transaction.setPurpose(Transaction.Purpose.USER_PAYMENT);
        dltTransaction.setSignedTransaction(DatatypeConverter.printHexBinary(transaction.bitcoinSerialize()));
    }

    private void sign(String fromAddress, String toAddress, InputStream stream, DltTransactionRequest dltTransaction) {
        Transaction transaction = new Transaction(this.networkParameters);
        Coin totalPayout = Coin.valueOf(dltTransaction.getAmount().longValue());
        int outputNumber = 2;
        if ((null != dltTransaction.getMessage() && !dltTransaction.getMessage().isEmpty())) {
            BitcoinData bitcoinData;
            try {
                switch (this.networkParameters.getAddressHeader()) {
                    case MAIN_ADDR:
                        bitcoinData = new BitcoinData(NETWORK.MAIN, stream, DATA_TYPE.TEXT, this.encryptor, this.compressor);
                        break;
                    case TEST_ADDR:
                        bitcoinData = new BitcoinData(NETWORK.TEST, stream, DATA_TYPE.TEXT, this.encryptor, this.compressor);
                        break;
                    default:
                        bitcoinData = new BitcoinData(NETWORK.TEST, stream, DATA_TYPE.TEXT, this.encryptor, this.compressor);
                        break;
                }
                for (String address : bitcoinData.getAddressList()) {
                    transaction.addOutput(Transaction.MIN_NONDUST_OUTPUT, Address.fromBase58(this.networkParameters, address));
                    totalPayout = totalPayout.add(Transaction.MIN_NONDUST_OUTPUT);
                }
                outputNumber += bitcoinData.getAddressList().size();
            } catch (DataOverSizeException e) {
                log.error(this.getClass().getSimpleName()+"#signTransaction()", e);
            } catch (IOException e) {
                log.error(this.getClass().getSimpleName()+"#signTransaction()", e);
            }
        }
        transaction.addOutput(Coin.valueOf(dltTransaction.getAmount().longValue()), Address.fromBase58(this.networkParameters, toAddress));
        totalPayout = totalPayout.add(Coin.valueOf(
                (null == dltTransaction.getFee()) ?
                        BitcoinFees.getInstance().calculate(FEE_POLICY.NORMAL, 1, outputNumber).longValue() :
                        dltTransaction.getFee().longValue()
        ));
        final Coin payout = Coin.valueOf(totalPayout.getValue());
        UTXO inputUtxo = this.utxoList.stream()
                .filter(utxo -> utxo.getAddress().equals(fromAddress) && utxo.getValue().isGreaterThan(payout))
                .findFirst().orElse(null);
        if (null == inputUtxo) {
            return;
        }
        transaction.addOutput(inputUtxo.getValue().subtract(totalPayout), Address.fromBase58(this.networkParameters,
                (null == dltTransaction.getChangeAddress()) ? fromAddress : dltTransaction.getChangeAddress()));
        TransactionOutPoint transactionOutPoint = new TransactionOutPoint(this.networkParameters, inputUtxo.getIndex(), inputUtxo.getHash());
        transaction.addSignedInput(transactionOutPoint, inputUtxo.getScript(), this.key, Transaction.SigHash.ALL, true);
        transaction.getConfidence().setSource(TransactionConfidence.Source.SELF);
        transaction.setPurpose(Transaction.Purpose.USER_PAYMENT);
        dltTransaction.setSignedTransaction(DatatypeConverter.printHexBinary(transaction.bitcoinSerialize()));
    }

    @Override
    public Account withNetwork(NETWORK network) {
        this.networkParameters = setNetwork(network);
        return this;
    }

    @Override
    public void setPrivateKey(BigInteger key) {
        if (null != key) {
            this.key = ECKey.fromPrivate(key);
        }
    }

    @Override
    public BigInteger getPrivateKey() {
        return this.key.getPrivKey();
    }

    @Override
    public void sign(String fromAddress, String toAddress, String message, DltTransaction dltTransaction) {
        if (dltTransaction instanceof DltTransactionRequest) {
            this.sign(fromAddress, toAddress, message.getBytes(), (DltTransactionRequest)dltTransaction);
        }
    }

    @Override
    public void sign(String fromAddress, String toAddress, byte[] message, DltTransaction dltTransaction) {
        if (dltTransaction instanceof DltTransactionRequest) {
            this.sign(fromAddress, toAddress, message, (DltTransactionRequest)dltTransaction);
        }
    }

    public void sign(String fromAddress, String toAddress, InputStream stream, DltTransaction dltTransaction) {
        if (dltTransaction instanceof DltTransactionRequest) {
            this.sign(fromAddress, toAddress, stream, (DltTransactionRequest)dltTransaction);
        }
    }

    /**
     * Add a UTXO to account
     * @param transactionHash String containing txHash of the UTXO
     * @param outpoint long containng vout/index of the UTXO
     * @param valueInSatoshi long containing amount of the UTXO
     * @param blockHeight int containing block height of the transaction
     * @param address String containing the address
     */
    public void addUtxo(String transactionHash, long outpoint, long valueInSatoshi, int blockHeight, String address) {
        if (null == this.utxoList) {
            this.utxoList = new ArrayList<>();
        }
        byte pubKey[] = Base58.decodeChecked(address);
        String pubKeyHex = String.format(
                SCRIPT_OP_DUP_OP_HASH160_PUBKEY_OP_EQUALVERIFY_OP_CHECKSIG,
                DatatypeConverter
                        .printHexBinary(ByteBuffer
                                .allocate(pubKey.length - 1)
                                .put(pubKey, 1, pubKey.length - 1)
                                .array()
                        )
                        .toLowerCase()
        );
        Script script = new Script(Utils.HEX.decode(pubKeyHex));
        this.utxoList.add(new UTXO(Sha256Hash.wrap(transactionHash), outpoint, Coin.valueOf(valueInSatoshi), blockHeight, false, script, address));
    }

    /**
     * Mapping Network with addressType
     * @param network NETWORK containing network enum
     * @return NetworkParameters containing Bitcoin parameter
     */
    private static NetworkParameters setNetwork(NETWORK network) {
        NetworkParameters networkParameters;
        switch (network) {
            case MAIN: networkParameters = MainNetParams.get(); break;
            case TEST: networkParameters = RegTestParams.get(); break;
            case KOVAN: networkParameters = TestNet3Params.get(); break;
            case RINKEBY: networkParameters = TestNet3Params.get(); break;
            case ROPSTEN: networkParameters = TestNet3Params.get(); break;
            default: networkParameters = MainNetParams.get(); break;
        }
        return networkParameters;
    }

    /**
     * Create Bitcoin account instance with new key
     * @param network NETWORK containing network param
     * @return Account containing a Bitcoin account
     */
    public static Account getInstance(NETWORK network) {
        I = new BitcoinAccount(setNetwork(network));
        return I;
    }

    /**
     * Get Bitcoin account instance by given secret key number
     * @param network NETWORK containing network param
     * @param privateKey BigInteger containing the key
     * @return Account containing a Bitcoin account
     */
    public static Account getInstance(NETWORK network, BigInteger privateKey) {
        I = new BitcoinAccount(setNetwork(network), privateKey);
        return I;
    }

    /**
     * Get Bitcoin account instance by given secret key array
     * @param network NETWORK containing network param
     * @param privateKey byte array containing the key in array
     * @return Account containing a Bitcoin account
     */
    public static Account getInstance(NETWORK network, byte privateKey[]) {
        I = new BitcoinAccount(setNetwork(network), privateKey);
        return I;
    }

    /**
     * Get Bitcoin account instance by given secret key object
     * @param network NETWORK containing network param
     * @param privateKey ECKey containing the key
     * @return Account containing a Bitcoin account
     */
    public static Account getInstance(NETWORK network, ECKey privateKey) {
        I = new BitcoinAccount(setNetwork(network), privateKey);
        return I;
    }

}
