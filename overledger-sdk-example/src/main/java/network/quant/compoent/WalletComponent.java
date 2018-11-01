package network.quant.compoent;

import network.quant.utils.UITools;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WalletComponent extends BaseComponent {

    public interface WalletHandler {

        void generate(TYPE type);

        void receive(TYPE type, String secretKey, String address);

    }

    public enum TYPE {

        btc, eth, xrp;

    }

    private static final Color TEXT = new Color(38, 31, 99);
    Image coin;
    JLabel secretKeyLabel = new JLabel("Secret Key");
    JLabel publicAddressLabel = new JLabel("Address");
    public JTextField secretKey = new JTextField();
    public JTextField publicAddress = new JTextField();
    Button generateButton = new Button("Generate", Button.TYPE.OK);
    Button receiveButton = new Button("Receive", Button.TYPE.NORMAL);
    WalletHandler walletHandler;

    public WalletComponent(Dimension dimension, TYPE type, WalletHandler walletHandler) {
        super(dimension);

        this.walletHandler = walletHandler;

        int componentIndex = 0;

        this.coin = Toolkit.getDefaultToolkit().getImage(
                Thread.currentThread().getContextClassLoader().getResource(String.format("%s.png", type.name()))
        ).getScaledInstance(32, 32, Image.SCALE_SMOOTH);

        this.secretKeyLabel.setSize(90, 32);
        this.secretKeyLabel.setLocation(100, 10);
        this.secretKeyLabel.setFont(UITools.getFont(Font.BOLD, 16));
        this.secretKeyLabel.setForeground(TEXT);
        this.secretKeyLabel.setHorizontalAlignment(JLabel.RIGHT);
        this.add(this.secretKeyLabel, componentIndex++);

        this.secretKey.setSize(dimension.width - 420, 32);
        this.secretKey.setLocation(200, 10);
        this.secretKey.setFont(UITools.getFont(Font.PLAIN, 16));
        this.secretKey.setForeground(TEXT);
        this.add(this.secretKey, componentIndex++);

        this.publicAddressLabel.setSize(90, 32);
        this.publicAddressLabel.setLocation(100, 50);
        this.publicAddressLabel.setFont(UITools.getFont(Font.BOLD, 16));
        this.publicAddressLabel.setForeground(TEXT);
        this.publicAddressLabel.setHorizontalAlignment(JLabel.RIGHT);
        this.add(this.publicAddressLabel, componentIndex++);

        this.publicAddress.setSize(dimension.width - 420, 32);
        this.publicAddress.setLocation(200, 50);
        this.publicAddress.setFont(UITools.getFont(Font.PLAIN, 16));
        this.publicAddress.setForeground(TEXT);
        this.add(this.publicAddress, componentIndex++);

        this.generateButton.setSize(150, 50);
        this.generateButton.setLocation(dimension.width - 150, 0);
        this.generateButton.addActionListener((event) -> {
            this.walletHandler.generate(type);
        });
        if (!type.equals(TYPE.xrp)) {
            this.add(this.generateButton, componentIndex++);
        }

        this.receiveButton.setSize(150, 50);
        this.receiveButton.setLocation(dimension.width - 150, dimension.height - 50);
        this.receiveButton.addActionListener((event) -> {
            this.walletHandler.receive(type, secretKey.getText(), publicAddress.getText());
        });
        this.add(this.receiveButton, componentIndex++);
    }

    @Override
    protected void paintComponent(Graphics2D g2D) {
        super.paintComponent(g2D);
        g2D.setColor(Color.WHITE);
        g2D.fillRoundRect(0, 0, this.getSize().width, this.getSize().height, 8, 8);
        g2D.drawImage(this.coin, 10, 10, this);
    }


}
