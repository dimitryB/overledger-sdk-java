package network.quant.compoent;

import network.quant.mvp.presenter.HeaderPresenter;
import network.quant.mvp.view.HeaderView;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HeaderPanel extends BaseComponent implements HeaderView {

    Image logo, subLogo, closeImage, walletImage, settingsImage, orderImage;
    JLabel closeLabel, walletLabel, settingsLabel, orderLabel;
    HeaderPresenter headerPresenter;

    private HeaderPanel() {
        super(new Dimension(1024, 82));
        this.logo = Toolkit.getDefaultToolkit().getImage(
                Thread.currentThread().getContextClassLoader().getResource("Quant-Logo.png")
        );
        this.subLogo = Toolkit.getDefaultToolkit().getImage(
                Thread.currentThread().getContextClassLoader().getResource("Overledger-Logo.png")
        );

        this.initialComponents();
    }

    private void initialComponents() {
        int componentIndex = 0;
        this.closeImage = Toolkit.getDefaultToolkit().getImage(
                Thread.currentThread().getContextClassLoader().getResource("close.png")
        ).getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        this.closeLabel = new JLabel(new ImageIcon(this.closeImage));
        this.closeLabel.setSize(24, 24);
        this.closeLabel.setLocation(this.getSize().width - 48, (int)( this.getSize().getHeight() - 24 ) / 2 - 5 );
        this.closeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.closeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (null != headerPresenter) {
                    headerPresenter.onExit();
                }
            }
        });
        this.add(this.closeLabel, componentIndex++);

        this.settingsImage = Toolkit.getDefaultToolkit().getImage(
                Thread.currentThread().getContextClassLoader().getResource("settings.png")
        ).getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        this.settingsLabel = new JLabel(new ImageIcon(this.settingsImage));
        this.settingsLabel.setSize(32, 32);
        this.settingsLabel.setLocation(this.getSize().width - 48 * 2, (int)( this.getSize().getHeight() - 32 ) / 2 - 5 );
        this.settingsLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.settingsLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (null != headerPresenter) {
                    headerPresenter.onGotoSettings();
                }
            }
        });
        this.add(this.settingsLabel, componentIndex++);

        this.walletImage = Toolkit.getDefaultToolkit().getImage(
                Thread.currentThread().getContextClassLoader().getResource("wallet.png")
        ).getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        this.walletLabel = new JLabel(new ImageIcon(this.walletImage));
        this.walletLabel.setSize(32, 32);
        this.walletLabel.setLocation(this.getSize().width - 48 * 3, (int)( this.getSize().getHeight() - 32 ) / 2 - 5 );
        this.walletLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.walletLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (null != headerPresenter) {
                    headerPresenter.onGotoWallet();
                }
            }
        });
        this.add(this.walletLabel, componentIndex++);

        this.orderImage = Toolkit.getDefaultToolkit().getImage(
                Thread.currentThread().getContextClassLoader().getResource("order.png")
        ).getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        this.orderLabel = new JLabel(new ImageIcon(this.orderImage));
        this.orderLabel.setSize(32, 32);
        this.orderLabel.setLocation(this.getSize().width - 48 * 4, (int)( this.getSize().getHeight() - 32 ) / 2 - 5 );
        this.orderLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.orderLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (null != headerPresenter) {
                    headerPresenter.onGotoOrder();
                }
            }
        });
        this.add(this.orderLabel, componentIndex++);
    }

    @Override
    protected void paintComponent(Graphics2D g2D) {
        super.paintComponent(g2D);
        g2D.setColor(Color.WHITE);
        g2D.fillRoundRect(0, 0, this.getSize().width, this.getSize().height-10, 16, 16);
        g2D.fillRect(0, this.getSize().height - 34, this.getSize().width, 24);
        g2D.drawImage(this.logo, 16, 8, this.logo.getWidth(this) / 4, this.logo.getHeight(this) / 4, this);
        g2D.drawImage(this.subLogo, 32 + this.logo.getWidth(this) / 4, 16, this.subLogo.getWidth(this) / 6, this.subLogo.getHeight(this) / 6, this);

        GradientPaint gp = new GradientPaint(0, 72, new Color(128, 128, 128, 128), 0, 82, new Color(128, 128, 128, 0));
        g2D.setPaint(gp);
        g2D.fillRect(0, 72, this.getSize().width, 10);
    }

    @Override
    public Component asComponent() {
        return this;
    }

    @Override
    public void setPresenter(HeaderPresenter headerPresenter) {
        this.headerPresenter = headerPresenter;
    }

    public static HeaderView newInstance() {
        return new HeaderPanel();
    }

}
