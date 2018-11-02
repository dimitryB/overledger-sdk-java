package network.quant.essential.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.xml.bind.DatatypeConverter;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DltBytesTransactionRequest extends DltTransactionRequest {

    byte bytes[];

    public DltTransactionRequest asDltTransactionRequest() {
        return DltTransactionRequest
                .builder()
                .dlt(this.getDlt())
                .fromAddress(this.getFromAddress())
                .toAddress(this.getToAddress())
                .changeAddress(this.getChangeAddress())
                .message(DatatypeConverter.printHexBinary(this.bytes))
                .amount(this.getAmount())
                .fee(this.getFee())
                .feeLimit(this.getFeeLimit())
                .callbackUrl(this.getCallbackUrl())
                .signedTransaction(this.getSignedTransaction())
                .sequence(this.getSequence())
                .build();
    }

}
