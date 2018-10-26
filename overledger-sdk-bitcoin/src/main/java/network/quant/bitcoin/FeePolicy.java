package network.quant.bitcoin;

import network.quant.api.FEE_POLICY;
import java.math.BigInteger;

/**
 * Fee Policy definition, Bitcoin accept 3 typs of fee policy
 */
public interface FeePolicy {

    /**
     * Calculate Bitcoin fee
     * @param fee_policy FEE_POLICY containing fee policy
     * @param inputs int containing number of inputs
     * @param outputs int containing number of outputs
     * @return BigInteger containing Bitcoin fee
     */
    BigInteger calculate(FEE_POLICY fee_policy, int inputs, int outputs);

}
