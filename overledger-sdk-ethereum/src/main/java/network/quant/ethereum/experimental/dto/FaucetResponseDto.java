package network.quant.ethereum.experimental.dto;

import lombok.Data;
import java.math.BigInteger;

@Data
public class FaucetResponseDto {

    String address;
    String txhash;
    BigInteger amount;

}
