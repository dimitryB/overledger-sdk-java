package network.quant.ripple.model;

import com.ripple.core.coretypes.Amount;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RippleOutcome {

    String result;
    String timestamp;
    BigDecimal fee;
    Map<String, List<Amount>> balanceChanges;
    Object orderbookChanges;
    Long ledgerVersion;
    Long indexInLedger;
    RippleAmount deliveredAmount;

}
