package network.quant.bitcoin.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BitcoinVout {

    BitcoinScriptPubKey scriptPubKey;
    BigDecimal value;
    Integer n;

}
