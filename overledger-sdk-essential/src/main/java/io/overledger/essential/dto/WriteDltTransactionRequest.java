package io.overledger.essential.dto;

import io.overledger.api.DltTransactionRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigInteger;

/**
 * DLT transaction for write request
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WriteDltTransactionRequest implements DltTransactionRequest {

    String dlt;
    String fromAddress;
    String toAddress;
    String changeAddress;
    String message;
    BigInteger amount;
    BigInteger fee;
    BigInteger feeLimit;
    String callbackUrl;
    String signedTransaction;
    Long sequence;

}
