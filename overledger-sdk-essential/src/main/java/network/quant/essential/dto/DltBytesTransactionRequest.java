package network.quant.essential.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DltBytesTransactionRequest extends DltTransactionRequest {

    @JsonIgnore
    byte bytes[];

}
