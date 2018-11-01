package network.quant.essential.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import java.io.InputStream;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DltStreamTransactionRequest extends DltTransactionRequest {

    InputStream inputStream;

}
