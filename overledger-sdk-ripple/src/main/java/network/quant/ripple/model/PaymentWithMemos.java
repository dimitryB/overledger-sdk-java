package network.quant.ripple.model;

import com.ripple.core.fields.Field;
import com.ripple.core.fields.STArrayField;
import com.ripple.core.fields.STObjectField;
import com.ripple.core.types.known.tx.txns.Payment;

public class PaymentWithMemos extends Payment {

    static public STArrayField Memos = starrayField(Field.Memos);
    static public STObjectField Memo = stobjectField(Field.Memo);

    public static STArrayField starrayField(final Field f) {
        return new STArrayField(){ @Override public Field getField() {return f;}};
    }

    public static STObjectField stobjectField(final Field f) {
        return new STObjectField() {
            @Override
            public Field getField() {
                return f;
            }
        };
    }

}
