package network.quant.compoent;

import network.quant.utils.UITools;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BikeCard extends BaseComponent {

    private static final Color BASE = new Color(38, 31, 99);
    private static final Color NORMA = new Color(59, 222, 200);
    Polygon polygon;
    Image image;
    JLabel name, price;

    public BikeCard(Image image, String name, String price) {
        super(new Dimension(300, 400));

        this.image = image.getScaledInstance(280, 280 * image.getHeight(this) / image.getWidth(this), Image.SCALE_SMOOTH);

        int xPolygon[] = {200, 300, 300};
        int yPolygon[] = {0, 100, 0};
        this.polygon = new Polygon(xPolygon, yPolygon, 3);

        this.name = new JLabel(name);
        this.name.setForeground(Color.WHITE);
        this.name.setHorizontalAlignment(JLabel.CENTER);
        this.name.setVerticalAlignment(JLabel.CENTER);
        this.name.setSize(300, 56);
        this.name.setFont(UITools.getFont(Font.PLAIN, 24));

        this.price = new JLabel(price);
        this.price.setForeground(Color.WHITE);
        this.price.setHorizontalAlignment(JLabel.CENTER);
        this.price.setVerticalAlignment(JLabel.CENTER);
        this.price.setSize(100, 56);
        this.price.setFont(UITools.getFont(Font.PLAIN, 16));

        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics2D g2D) {
        super.paintComponent(g2D);
        g2D.setColor(Color.WHITE);
        g2D.fillRoundRect(0, 0, this.getSize().width, this.getSize().height, 16, 16);
        g2D.drawImage(this.image, 10, 10, this);

        g2D.setColor(BASE);
        g2D.fillRoundRect(0, this.getSize().height - 56, this.getSize().width, 56, 16, 16);
        g2D.fillRect(0, this.getSize().height - 56, this.getSize().width, 20);
        this.name.paint(g2D.create(0, this.getSize().height - 56, 300, 56));

        g2D.setColor(NORMA);
        g2D.fillPolygon(this.polygon);
        this.price.paint(g2D.create(215, 0, 100, 56));
    }

}
