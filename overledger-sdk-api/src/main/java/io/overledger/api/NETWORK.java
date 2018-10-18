package io.overledger.api;

/**
 * Definition of network address
 */
public enum NETWORK {

    MAIN(0x00, 1),
    ROPSTEN(0x6F, 3),
    KOVAN(0x6F, 42),
    RINKEBY(0x6F, 4),
    TEST(0x6F, 1337);

    private int addressType;
    private int chainId;

    NETWORK(int btcCode, int chainId) {
        this.addressType = btcCode;
        this.chainId = chainId;
    }

    /**
     * Get Bitcoin network address type
     * @return int containing the type
     */
    public int getAddressType() {
        return this.addressType;
    }

    /**
     * Get Ethereum network chain Id
     * @return int containing the chainId
     */
    public int getChainId() {
        return this.chainId;
    }

}
