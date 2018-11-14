package network.quant.mvp.impl;

import network.quant.event.ApplicationExitHandler;
import network.quant.event.ApplicationHistoryHandler;
import network.quant.mvp.presenter.HeaderPresenter;
import network.quant.mvp.view.HeaderView;
import network.quant.mvp.view.View;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class HeaderPresenterImpl implements HeaderPresenter {

    HeaderView headerView;
    ApplicationExitHandler applicationExitHandler;
    ApplicationHistoryHandler applicationHistoryHandler;

    private HeaderPresenterImpl(HeaderView headerView, ApplicationHistoryHandler applicationHistoryHandler, ApplicationExitHandler applicationExitHandler) {
        this.headerView = headerView;
        this.headerView.setPresenter(this);
        this.applicationExitHandler = applicationExitHandler;
        this.applicationHistoryHandler = applicationHistoryHandler;
    }

    @Override
    public void onExit() {
        this.applicationExitHandler.onExit();
    }

    @Override
    public void onGotoSettings() {
        this.applicationHistoryHandler.onGoto(ANCHOR.SETTINGS);
    }

    @Override
    public void onGotoWallet() {
        this.applicationHistoryHandler.onGoto(ANCHOR.WALLET);
    }

    @Override
    public void onGotoOrder() {
        this.applicationHistoryHandler.onGoto(ANCHOR.ORDER);
    }

    @Override
    public View asView() {
        return this.headerView;
    }

    public static HeaderPresenter newInstance(HeaderView headerView, ApplicationHistoryHandler applicationHistoryHandler, ApplicationExitHandler applicationExitHandler) {
        return new HeaderPresenterImpl(headerView, applicationHistoryHandler, applicationExitHandler);
    }

}
