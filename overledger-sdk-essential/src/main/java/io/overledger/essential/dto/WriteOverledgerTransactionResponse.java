package io.overledger.essential.dto;

import io.overledger.api.OverledgerTransaction;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WriteOverledgerTransactionResponse implements OverledgerTransaction {

    UUID overledgerTransactionId;
    String  mappId;
    String timestamp;
    List<WriteDltTransactionResponse> dltData;

}
