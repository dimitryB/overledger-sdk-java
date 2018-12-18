package network.quant.ripple.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import network.quant.util.Transaction;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RippleTransaction implements Transaction {

    String dlt;
    RippleData data;

}
