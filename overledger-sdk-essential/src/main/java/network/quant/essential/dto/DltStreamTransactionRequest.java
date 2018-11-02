package network.quant.essential.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import java.io.InputStream;

@Slf4j
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DltStreamTransactionRequest extends DltTransactionRequest {

    InputStream inputStream;

    public DltTransactionRequest asDltTransactionRequest() {
        return DltTransactionRequest
                .builder()
                .dlt(this.getDlt())
                .fromAddress(this.getFromAddress())
                .toAddress(this.getToAddress())
                .changeAddress(this.getChangeAddress())
                .message(this.getMessage())
                .amount(this.getAmount())
                .fee(this.getFee())
                .feeLimit(this.getFeeLimit())
                .callbackUrl(this.getCallbackUrl())
                .signedTransaction(this.getSignedTransaction())
                .sequence(this.getSequence())
                .build();
    }

}
