package network.quant;

import network.quant.mvp.Factory;

final class OverledgerEntryPoint {

    static { Factory.I.config(); }

    public static void main(String[] args) {
        new OverledgerEntryPoint();
    }

}
