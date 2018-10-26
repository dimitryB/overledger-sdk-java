package network.quant.bitcoin;

import network.quant.api.FEE_POLICY;
import network.quant.bitcoin.exception.BitcoinFeesRequestFailedException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import java.math.BigInteger;

/**
 * Helper class for calculating Bitcoin fee
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class BitcoinFees implements FeePolicy {

    private static final String FEE_URL = "https://bitcoinfees.earn.com/api/v1/fees/recommended";
    private static final long REFRESH_TIME = 1000 * 60 * 5;
    private static BitcoinFees I;
    long lastUpdate = -1;
    WebClient webClient = WebClient.create(FEE_URL);
    BigInteger priority = new BigInteger("6");
    BigInteger normal = new BigInteger("4");
    BigInteger economy = new BigInteger("2");

    private BitcoinFees() {}

    private void getFees() {
        BitcoinFeesRecommended recommended = this.webClient.get().retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> clientResponse
                        .bodyToMono(ByteArrayResource.class)
                        .map(ByteArrayResource::getByteArray)
                        .map(String::new)
                        .map(BitcoinFeesRequestFailedException::new)
                )
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> clientResponse
                        .bodyToMono(ByteArrayResource.class)
                        .map(ByteArrayResource::getByteArray)
                        .map(String::new)
                        .map(BitcoinFeesRequestFailedException::new)
                )
                .bodyToMono(BitcoinFeesRecommended.class).block();
        if (null != recommended) {
            this.priority = BigInteger.valueOf(recommended.getFastestFee());
            this.normal = BigInteger.valueOf(recommended.getHalfHourFee());
            this.economy = BigInteger.valueOf(recommended.getHourFee());
        }
    }

    private void updateFees() {
        long now = System.currentTimeMillis();
        if (this.lastUpdate < 0 || now - this.lastUpdate > REFRESH_TIME) {
            this.getFees();
            this.lastUpdate = now;
        }
    }

    @Override
    public BigInteger calculate(FEE_POLICY fee_policy, int inputs, int outputs) {
        try {
            this.updateFees();
        } catch (Exception e) {
            log.warn("Unable to connect to bitcoinfees service", e);
        }
        int bytes = inputs * 180 + outputs * 34 + 10;
        switch (fee_policy) {
            case PRIORITY: return BigInteger.valueOf(bytes + inputs).multiply(this.priority);
            case NORMAL: return BigInteger.valueOf(bytes - inputs).multiply(this.normal);
            case ECONOMY: return BigInteger.valueOf(bytes + inputs * ((0 == System.currentTimeMillis() % 2) ? -1 : 1)).multiply(this.economy);
            default: return BigInteger.valueOf(bytes - inputs).multiply(this.normal);
        }
    }

    static BitcoinFees getInstance() {
        if (null == I) {
            I = new BitcoinFees();
        }
        return I;
    }

}
