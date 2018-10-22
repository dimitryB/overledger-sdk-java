package io.overledger.essential.dto;

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
public class DltTransactionRequest implements io.overledger.api.DltTransactionRequest {

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
