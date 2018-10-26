package network.quant.bitcoin;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.bitcoinj.core.Coin;

/**
 * Transaction output model
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BitcoinOutput {

    String address;
    Coin amount;

}
