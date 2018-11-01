package network.quant.event;

import network.quant.mvp.impl.ANCHOR;

public interface ApplicationHistoryChangeHandler {

    void changeTo(ANCHOR anchor);

}
