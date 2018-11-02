package network.quant.ethereum;

import network.quant.api.FEE_POLICY;
import network.quant.ethereum.exception.EthGasStationRequestFailedException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import java.math.BigInteger;

/**
 * Helper class for calculating Ethereum gas
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class EthGasStation implements FeePolicy {

    private static final String GAS_URL = "https://ethgasstation.info/json/ethgasAPI.json";
    private static final long REFRESH_TIME = 1000 * 60;
    private static EthGasStation I;
    long lastUpdate = -1;
    WebClient webClient = WebClient.create(GAS_URL);
    BigInteger priority = new BigInteger("230");
    BigInteger normal = new BigInteger("125");
    BigInteger economy = new BigInteger("120");

    private EthGasStation() {}

    private void getGases() {
        EthGasAPI ethGasAPI = this.webClient.get().retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> clientResponse
                        .bodyToMono(ByteArrayResource.class)
                        .map(ByteArrayResource::getByteArray)
                        .map(String::new)
                        .map(EthGasStationRequestFailedException::new)
                )
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> clientResponse
                        .bodyToMono(ByteArrayResource.class)
                        .map(ByteArrayResource::getByteArray)
                        .map(String::new)
                        .map(EthGasStationRequestFailedException::new)
                )
                .bodyToMono(EthGasAPI.class).block();
        if (null != ethGasAPI) {
            this.priority = BigInteger.valueOf(ethGasAPI.getFastest().longValue());
            this.normal = BigInteger.valueOf(ethGasAPI.getFast().longValue());
            this.economy = BigInteger.valueOf(ethGasAPI.getAverage().longValue());
        }
    }

    private void updateGases() {
        long now = System.currentTimeMillis();
        if (this.lastUpdate < 0 || now - this.lastUpdate > REFRESH_TIME) {
            try {
                this.getGases();
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.lastUpdate = now;
        }
    }

    @Override
    public BigInteger calculate(FEE_POLICY fee_policy) {
        try {
            this.updateGases();
        } catch (Exception e) {
            log.warn("Unable to connect to ethgasstation service", e);
        }
        switch (fee_policy) {
            case PRIORITY: return this.priority;
            case NORMAL: return this.normal;
            case ECONOMY: return this.economy;
            default: return this.normal;
        }
    }

    static EthGasStation getInstance() {
        if (null == I) {
            I = new EthGasStation();
        }
        return I;
    }

}
