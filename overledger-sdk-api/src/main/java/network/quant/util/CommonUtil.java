package network.quant.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Common Utility
 */
public class CommonUtil {

    private static final int KB = 1024;

    public static byte[] getStream(InputStream stream) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(stream.available());
        byte bytes[] = new byte[KB];
        int read;
        while ((read = stream.read(bytes)) > 0) {
            byteBuffer.put(bytes, 0, read);
        }
        return byteBuffer.array();
    }

}
