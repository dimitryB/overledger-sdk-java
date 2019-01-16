package network.quant.compoent;

import network.quant.mvp.view.View;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RootPanel extends BaseComponent {

    Image ribbon;

    public RootPanel(View headerView, View contentView) {
        super(new Dimension(1024, 768));

        this.ribbon = Toolkit.getDefaultToolkit().getImage(
                Thread.currentThread().getContextClassLoader().getResource("Overledger-Solution-Ribbon.png")
        );

        this.add(contentView.asComponent(), 0);
        this.add(headerView.asComponent(), 1);
        contentView.asComponent().setLocation(0, 72);
    }

    @Override
    protected void paintComponent(Graphics2D g2D) {
        super.paintComponent(g2D);
        g2D.drawImage(this.ribbon, 0, 150, this.getSize().width, this.getSize().height-200, this);
        g2D.setColor(new Color(220, 220, 220, 220));
        g2D.fillRoundRect(0, 0, this.getSize().width, this.getSize().height, 16, 16);
    }

    public static RootPanel newInstance(View headerView, View contentView) {
        return new RootPanel(headerView, contentView);
    }

}
