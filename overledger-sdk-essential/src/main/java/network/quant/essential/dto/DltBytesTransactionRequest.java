package network.quant.essential.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DltBytesTransactionRequest extends DltTransactionRequest {

    byte bytes[];

}
