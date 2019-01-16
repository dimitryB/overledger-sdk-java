package network.quant.ripple.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RippleData {

    String type;
    String address;
    Long sequence;
    String id;
    RippleSpecification specification;
    RippleOutcome outcome;

}
