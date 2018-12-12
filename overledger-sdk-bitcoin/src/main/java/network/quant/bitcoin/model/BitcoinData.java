package network.quant.bitcoin.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BitcoinData {

    Long vsize;
    Long size;
    Long locktime;
    String txid;
    List<BitcoinVin> vin;
    Integer version;
    String hash;
    List<BitcoinVout> vout;

}
