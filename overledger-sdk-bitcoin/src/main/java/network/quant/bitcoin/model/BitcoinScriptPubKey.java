package network.quant.bitcoin.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BitcoinScriptPubKey {

    List<String> addresses;
    String asm;
    String hex;
    String type;
    Integer reqSigs;

}
