package network.quant.mvp;

import network.quant.event.ApplicationHistoryHandler;
import network.quant.mvp.impl.ApplicationFactory;

public interface Factory {

    Factory I = ApplicationFactory.getInstance();

    void config();

    ApplicationHistoryHandler getHistoryHandler();

}
