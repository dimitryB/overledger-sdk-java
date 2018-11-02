package network.quant.bitcoin;

import network.quant.api.DATA_TYPE;
import network.quant.api.ENCRYPT_TYPE;
import network.quant.api.NETWORK;
import network.quant.exception.AddressChecksumNotMatchException;
import network.quant.exception.NetworkNotMatchException;
import network.quant.exception.UnknownDataException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BitcoinHeaderTest {

    private long length;

    @Before
    public void setup() {
        this.length = 1024;
    }

    @Test
    public void test001CreateBitcoinHeader() throws UnknownDataException, AddressChecksumNotMatchException, NetworkNotMatchException {
        BitcoinHeader testHeader = new BitcoinHeader(NETWORK.TEST, DATA_TYPE.TEXT, ENCRYPT_TYPE.NONE, false, this.length);
        String testAddress = testHeader.getAddress();
        Assert.assertNotNull(testAddress);
        Assert.assertEquals('m', testAddress.charAt(0));
        BitcoinHeader verifyHeader = new BitcoinHeader(NETWORK.TEST, testAddress);
        Assert.assertEquals(DATA_TYPE.TEXT, verifyHeader.getType());
        Assert.assertEquals(ENCRYPT_TYPE.NONE, verifyHeader.getEncryption());
        Assert.assertEquals(false, verifyHeader.getCompressed());
        Assert.assertEquals(this.length, verifyHeader.getLength());
    }

}
