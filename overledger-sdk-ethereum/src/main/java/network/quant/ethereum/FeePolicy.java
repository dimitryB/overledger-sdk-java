package network.quant.ethereum;

import network.quant.api.FEE_POLICY;
import java.math.BigInteger;

/**
 * Fee Policy definition, Ethereum accept 3 typs of fee policy
 */
public interface FeePolicy {

    /**
     * Calculate Bitcoin fee
     * @param fee_policy FEE_POLICY containing fee policy
     * @return BigInteger containing Ethereum fee
     */
    BigInteger calculate(FEE_POLICY fee_policy);

}
