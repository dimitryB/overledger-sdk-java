package io.overledger.essential.util;

import io.overledger.api.Compressor;
import lombok.extern.slf4j.Slf4j;
import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Slf4j
public class ZlibCompressor implements Compressor {

    private ZlibCompressor() {}

    @Override
    public byte[] compress(byte[] bytes) {
        byte[] result;
        try {
            Deflater deflater = new Deflater();
            deflater.setLevel(Deflater.BEST_COMPRESSION);
            deflater.setInput(bytes);
            deflater.finish();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(bytes.length);
            byte buffer[] = new byte[2048];
            while (!deflater.finished()) {
                int size = deflater.deflate(buffer);
                byteArrayOutputStream.write(buffer, 0, size);
            }
            byteArrayOutputStream.close();
            result = byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            log.error("Unable to compress target", e);
            result = null;
        }
        return result;
    }

    @Override
    public byte[] decompress(byte[] bytes) {
        byte[] result;
        try {
            Inflater inflater = new Inflater();
            inflater.setInput(bytes);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(bytes.length);
            byte buffer[] = new byte[2048];
            while (!inflater.finished()) {
                int size = inflater.inflate(buffer);
                byteArrayOutputStream.write(buffer, 0, size);
            }
            byteArrayOutputStream.close();
            result = byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            log.error("Unable to decompress target", e);
            result = null;
        }
        return result;
    }

    public static Compressor newInstance() {
        return new ZlibCompressor();
    }

}
