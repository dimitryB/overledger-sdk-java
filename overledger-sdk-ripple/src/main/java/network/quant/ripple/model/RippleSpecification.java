package network.quant.ripple.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RippleSpecification {

    RippleSource source;
    RippleDestination destination;
    List<RippleMemo> memos;

}
