package network.quant.essential.dto;

import network.quant.api.OverledgerTransaction;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OverledgerTransactionResponse implements OverledgerTransaction {

    UUID overledgerTransactionId;
    String  mappId;
    String timestamp;
    List<DltTransactionResponse> dltData;

}
