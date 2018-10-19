package io.overledger.essential.dto;

import io.overledger.api.DLTStatus;
import io.overledger.api.DltTransactionResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * DLT transaction for write response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WriteDltTransactionResponse implements DltTransactionResponse {

    String dlt;
    String transactionHash;
    Integer blockHeight;
    DLTStatus status;
    String links[];

}
