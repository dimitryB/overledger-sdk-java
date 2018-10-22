package io.overledger.essential.dto;

import io.overledger.api.DLTStatus;
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
public class DltTransactionResponse implements io.overledger.api.DltTransactionResponse {

    String dlt;
    String transactionHash;
    Integer blockHeight;
    DLTStatus status;
    String links[];

}
