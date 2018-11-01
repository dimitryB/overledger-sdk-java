package network.quant.ripple.model;

import com.ripple.core.coretypes.STObject;
import com.ripple.core.fields.BlobField;
import com.ripple.core.fields.Field;

public class TransactionMemo extends STObject {

    static public BlobField MemoData = blobField(Field.MemoData);
    static public BlobField MemoFormat = blobField(Field.MemoFormat);
    static public BlobField MemoType = blobField(Field.MemoType);

    public static BlobField blobField(final Field f) {
        return new BlobField() {
            @Override
            public Field getField() {
                return f;
            }
        };
    }

}
