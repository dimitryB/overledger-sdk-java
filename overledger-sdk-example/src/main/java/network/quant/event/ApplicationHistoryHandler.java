package network.quant.event;

import network.quant.mvp.impl.ANCHOR;

public interface ApplicationHistoryHandler {

    void onGoto(ANCHOR settings);

}
