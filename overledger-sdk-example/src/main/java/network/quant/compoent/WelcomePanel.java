package network.quant.compoent;

import network.quant.mvp.presenter.ContentPresenter;
import network.quant.utils.UITools;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WelcomePanel extends BaseComponent {

    private static final Color TEXT = new Color(38, 31, 99);
    ContentPresenter contentPresenter;
    BikeCard xsr900Card;
    BikeCard xsr700Card;
    BikeCard z900RSCard;

    public WelcomePanel(Dimension dimension, ContentPresenter contentPresenter) {
        super(dimension);
        this.contentPresenter = contentPresenter;

        int componentIndex = 0;

        JLabel label = new JLabel("Motorcycle Shop");
        label.setSize(dimension.width - 20, 64);
        label.setLocation(10, 10);
        label.setFont(UITools.getFont(Font.PLAIN, 48));
        label.setForeground(TEXT);
        this.add(label, componentIndex++);

        this.xsr900Card = new BikeCard(
                Toolkit.getDefaultToolkit().getImage(
                        Thread.currentThread().getContextClassLoader().getResource("bikes/XSR900.jpg")
                ),
                "XSR900", "1.6 BTC"
        );
        this.xsr900Card.setLocation(20, 100);
        this.xsr900Card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                contentPresenter.onGotoDetails(0);
            }

        });
        this.add(this.xsr900Card, componentIndex++);

        this.xsr700Card = new BikeCard(
                Toolkit.getDefaultToolkit().getImage(
                        Thread.currentThread().getContextClassLoader().getResource("bikes/XSR700.jpg")
                ),
                "XSR700", "1.2 BTC"
        );
        this.xsr700Card.setLocation(340, 100);
        this.xsr700Card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                contentPresenter.onGotoDetails(1);
            }

        });
        this.add(this.xsr700Card, componentIndex++);

        this.z900RSCard = new BikeCard(
                Toolkit.getDefaultToolkit().getImage(
                        Thread.currentThread().getContextClassLoader().getResource("bikes/Z900RS.jpg")
                ),
                "Z900RS", "2 BTC"
        );
        this.z900RSCard.setLocation(660, 100);
        this.z900RSCard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                contentPresenter.onGotoDetails(2);
            }

        });
        this.add(this.z900RSCard, componentIndex++);
    }

}
