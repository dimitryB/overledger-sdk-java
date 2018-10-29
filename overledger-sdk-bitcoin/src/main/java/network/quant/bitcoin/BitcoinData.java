package network.quant.bitcoin;

import network.quant.api.*;
import network.quant.bitcoin.exception.BitcoinDataNotMatchingLengthException;
import network.quant.exception.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import network.quant.util.CommonUtil;
import java.io.*;
import java.util.List;

/**
 * Data is encoded and saved in Bitcoin address
 * This class provides methods to operate encoded data
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BitcoinData {

    private static final int MAX_SIZE = 50000;
    NETWORK network;
    Encryptor encryptor;
    Compressor compressor;
    @Getter
    List<String> addressList;
    @Getter
    byte data[];
    DATA_TYPE type;

    /**
     * Construct BitcoinData from address list
     * @param network NETWORK containing network address
     * @param addressList String list containing all data address
     * @throws UnknownDataException throw when unable to decode the address
     * @throws AddressChecksumNotMatchException throw when address checksum failed
     * @throws NetworkNotMatchException throw when network address does not match actual address
     * @throws DataNotFoundException throw when no Quant message not found
     * @throws BitcoinDataNotMatchingLengthException throw when cannot parse address list into data
     */
    public BitcoinData(NETWORK network, List<String> addressList, Encryptor encryptor, Compressor compressor) throws UnknownDataException, AddressChecksumNotMatchException, NetworkNotMatchException, DataNotFoundException, BitcoinDataNotMatchingLengthException {
        this.network = network;
        this.addressList = addressList;
        this.encryptor = encryptor;
        this.compressor = compressor;
        this.setData();
        if (null != this.compressor) {
            this.data = this.compressor.decompress(this.data);
        }
        if (null != this.encryptor) {
            this.data = this.encryptor.decrypt(this.data);
        }
    }

    /**
     * Construct BitcoinData from data
     * @param network NETWORK containing network address
     * @param data byte array containing data
     * @param type String containing DLT type
     * @throws DataOverSizeException throw when data size is over 50KB
     */
    public BitcoinData(NETWORK network, byte data[], DATA_TYPE type, Encryptor encryptor, Compressor compressor) throws DataOverSizeException {
        if (data.length > MAX_SIZE) {
            throw new DataOverSizeException();
        }
        this.network = network;
        this.data = data;
        this.type = type;
        this.encryptor = encryptor;
        this.compressor = compressor;
        if (null != this.encryptor) {
            this.data = this.encryptor.encrypt(this.data);
        }
        if (null != this.compressor) {
            this.data = this.compressor.compress(this.data);
        }
        this.setAddressList();
    }

    /**
     * Construct BitcoinData from data stream
     * @param network NETWORK containing network address
     * @param stream InputStream containing the data stream
     * @param type String containing DLT type
     * @throws DataOverSizeException throw when data size is over 50KB
     * @throws IOException throw when fail to read from stream
     */
    public BitcoinData(NETWORK network, InputStream stream, DATA_TYPE type, Encryptor encryptor, Compressor compressor) throws DataOverSizeException, IOException {
        if (stream.available() > MAX_SIZE) {
            throw new DataOverSizeException();
        }
        this.network = network;
        this.data = CommonUtil.getStream(stream);
        this.type = type;
        this.encryptor = encryptor;
        this.compressor = compressor;
        if (null != this.encryptor) {
            this.data = this.encryptor.encrypt(this.data);
        }
        if (null != this.compressor) {
            this.data = this.compressor.compress(this.data);
        }
        this.setAddressList();
    }

    private void setData() throws UnknownDataException, AddressChecksumNotMatchException, NetworkNotMatchException, DataNotFoundException, BitcoinDataNotMatchingLengthException {
        if (this.addressList.size() < 2) {
            throw new DataNotFoundException();
        } else {
            BitcoinHeader bitcoinHeader = new BitcoinHeader(this.network, this.addressList.remove(0));
            this.data = BitcoinUtils.getData(this.addressList, Math.toIntExact(bitcoinHeader.getLength()));
        }
    }

    private void setAddressList() {
        BitcoinHeader bitcoinHeader = new BitcoinHeader(
                this.network,
                this.type,
                null == this.encryptor ? ENCRYPT_TYPE.NONE : this.encryptor.getType(),
                null != this.compressor,
                this.data.length);
        this.addressList = BitcoinUtils.getAddress(this.network, this.data);
        this.addressList.add(0, bitcoinHeader.getAddress());
    }

}
