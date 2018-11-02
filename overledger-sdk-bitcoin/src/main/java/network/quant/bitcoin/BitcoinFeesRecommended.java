package network.quant.bitcoin;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * BitcoinFees.com response mapping model
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
class BitcoinFeesRecommended {
    long fastestFee;
    long halfHourFee;
    long hourFee;
}
