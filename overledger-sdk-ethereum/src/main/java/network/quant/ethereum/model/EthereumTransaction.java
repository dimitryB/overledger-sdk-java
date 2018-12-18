package network.quant.ethereum.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import network.quant.util.Transaction;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EthereumTransaction implements Transaction {

    String dlt;
    EthereumData data;

}
