package network.quant.bitcoin.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BitcoinVin {

    Long sequence;
    BitcoinScriptSig scriptSig;
    String txid;
    Integer vout;

}
