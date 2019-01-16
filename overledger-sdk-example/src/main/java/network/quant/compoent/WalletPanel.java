package network.quant.compoent;

import network.quant.mvp.presenter.ContentPresenter;
import network.quant.utils.UITools;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WalletPanel extends BaseComponent implements WalletComponent.WalletHandler {

    private static final Color TEXT = new Color(38, 31, 99);
    ContentPresenter contentPresenter;
    public WalletComponent bitcoinWallet;
    public WalletComponent ethereumWallet;
    public WalletComponent rippleWallet;
    Button okButton = new Button("OK", Button.TYPE.OK);
    Button cancelButton = new Button("CANCEL", Button.TYPE.CANCEL);

    public WalletPanel(Dimension dimension, ContentPresenter contentPresenter) {
        super(dimension);
        this.contentPresenter = contentPresenter;

        int componentIndex = 0;

        JLabel label = new JLabel("Wallet");
        label.setSize(dimension.width - 20, 64);
        label.setLocation(10, 10);
        label.setFont(UITools.getFont(Font.PLAIN, 48));
        label.setForeground(TEXT);
        this.add(label, componentIndex++);

        this.bitcoinWallet = new WalletComponent(new Dimension(dimension.width-40, 100), WalletComponent.TYPE.bitcoin, this);
        this.bitcoinWallet.setLocation(20, 100);
        this.add(this.bitcoinWallet, componentIndex++);

        this.ethereumWallet = new WalletComponent(new Dimension(dimension.width-40, 100), WalletComponent.TYPE.ethereum, this);
        this.ethereumWallet.setLocation(20, 225);
        this.add(this.ethereumWallet, componentIndex++);

        this.rippleWallet = new WalletComponent(new Dimension(dimension.width-40, 100), WalletComponent.TYPE.ripple, this);
        this.rippleWallet.setLocation(20, 350);
        this.add(this.rippleWallet, componentIndex++);

        this.okButton.setSize(150, 48);
        this.okButton.setLocation(dimension.width - 150, dimension.height - 50);
        this.okButton.addActionListener((event) -> {
            this.contentPresenter.onGotoMain();
        });
        this.add(this.okButton, componentIndex++);

        this.cancelButton.setSize(150, 48);
        this.cancelButton.setLocation(dimension.width - 320, dimension.height - 50);
        this.cancelButton.addActionListener((event) -> {
            this.contentPresenter.onGotoMain();
        });
        this.add(this.cancelButton, componentIndex++);
    }

    @Override
    public void generate(WalletComponent.TYPE type) {
        this.contentPresenter.generate(type.name());
    }

    @Override
    public void receive(WalletComponent.TYPE type) {
        this.contentPresenter.receive(type.name());
    }
}
