package network.quant.essential.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import java.io.InputStream;

@Slf4j
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DltStreamTransactionRequest extends DltTransactionRequest {

    @JsonIgnore
    InputStream inputStream;

}
