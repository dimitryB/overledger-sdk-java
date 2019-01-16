package network.quant.ripple.model;

import com.ripple.core.coretypes.Amount;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RippleSource {

    String address;
    Amount maxAmount;

}
