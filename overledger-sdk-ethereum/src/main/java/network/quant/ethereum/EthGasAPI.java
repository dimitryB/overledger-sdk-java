package network.quant.ethereum;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Ethgasstation.com response mapping model
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EthGasAPI {
    BigDecimal average;
    BigDecimal fastestWait;
    BigDecimal fastWait;
    BigDecimal fast;
    BigDecimal safeLowWait;
    BigInteger blockNum;
    BigDecimal avgWait;
    BigDecimal block_time;
    BigDecimal speed;
    BigDecimal fastest;
    BigDecimal safeLow;
}
