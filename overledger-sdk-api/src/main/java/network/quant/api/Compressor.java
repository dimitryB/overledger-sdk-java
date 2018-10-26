package network.quant.api;

/**
 * Definition of Compressor
 * Implement this interface for data compression support
 * The compression process has to be OFFLINE, make sure to implement matched compress and decompress methods,
 * BPI layer has no information about compressor, it can NOT compress or decompress data automatically
 * Account could use compressor for data compression.
 */
public interface Compressor {

    /**
     * Compress data
     * @param bytes byte array containing original data
     * @return byte array containing compressed data
     */
    byte[] compress(byte bytes[]);

    /**
     * Decompress data
     * @param bytes byte array containing compressed data
     * @return byte array containing original data
     */
    byte[] decompress(byte bytes[]);

}
