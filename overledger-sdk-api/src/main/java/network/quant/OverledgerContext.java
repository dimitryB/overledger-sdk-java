package network.quant;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Overledger Context contains the BPI key and Mapp ID
 * All Overledger operations required OverledgerContext to be setup first
 */
public final class OverledgerContext {

    private static final String BASE_URL_KEY                                = "overledger.baseurl";
    private static final String WRITE_TRANSACTIONS_KEY                      = "overledger.write";
    private static final String READ_TRANSACTIONS_BY_TRANSACTION_ID_KEY     = "overledger.read.id";
    private static final String READ_TRANSACTIONS_BY_MAPP_ID_KEY            = "overledger.read.mappid";
    private static final String READ_TRANSACTIONS_BY_TRANSACTION_HASH_KEY   = "overledger.read.txhash";
    private static final String BPI_KEY_KEY                                 = "overledger.bpikey";
    private static final String MAPP_KEY                                    = "overledger.mappid";
    public static String WRITE_TRANSACTIONS;
    public static String READ_TRANSACTIONS_BY_TRANSACTION_ID;
    public static String READ_TRANSACTIONS_BY_MAPP_ID;
    public static String READ_TRANSACTIONS_BY_TRANSACTION_HASH;
    public static String BPI_KEY;
    public static String MAPP_ID;

    /**
     * Load context from properties Object
     * @param properties Properties containing properties
     */
    public static void loadContext(Properties properties) {
        String baseUrl = properties.getProperty(BASE_URL_KEY);
        WRITE_TRANSACTIONS = String.format("%s%s", baseUrl, properties.getProperty(WRITE_TRANSACTIONS_KEY));;
        READ_TRANSACTIONS_BY_TRANSACTION_ID = String.format("%s%s", baseUrl, properties.getProperty(READ_TRANSACTIONS_BY_TRANSACTION_ID_KEY));
        READ_TRANSACTIONS_BY_MAPP_ID = String.format("%s%s", baseUrl, properties.getProperty(READ_TRANSACTIONS_BY_MAPP_ID_KEY));
        READ_TRANSACTIONS_BY_TRANSACTION_HASH = String.format("%s%s", baseUrl, properties.getProperty(READ_TRANSACTIONS_BY_TRANSACTION_HASH_KEY));
        BPI_KEY = properties.getProperty(BPI_KEY_KEY);
        MAPP_ID = properties.getProperty(MAPP_KEY);
    }

    /**
     * Load context from stream
     * @param inputStream InputStream containing the stream of properties
     * @throws IOException when failed to read properties file
     */
    public static void loadContext(InputStream inputStream) throws IOException {
        if (null != inputStream) {
            Properties properties = new Properties();
            properties.load(inputStream);
            loadContext(properties);
        }
    }

}
