package network.quant.event;

import network.quant.mvp.impl.ANCHOR;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplicationHistoryEvent implements ApplicationHistoryHandler {

    ApplicationHistoryChangeHandler applicationHistoryChangeHandler;

    public ApplicationHistoryEvent(ApplicationHistoryChangeHandler applicationHistoryChangeHandler) {
        this.applicationHistoryChangeHandler = applicationHistoryChangeHandler;
    }

    @Override
    public void onGoto(ANCHOR anchor) {
        this.applicationHistoryChangeHandler.changeTo(anchor);
    }

}
