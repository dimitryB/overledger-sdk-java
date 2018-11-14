package network.quant.compoent;

import network.quant.utils.UITools;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

@Slf4j
@FieldDefaults(level = AccessLevel.PROTECTED)
public class DetailComponent extends BaseComponent {

    protected static final Color TEXT = new Color(38, 31, 99);
    JLabel title;

    public DetailComponent(Dimension dimension) {
        super(dimension);

        this.title = new JLabel("Details");
        this.title.setSize(dimension.width - 20, 32);
        this.title.setLocation(10, 0);
        this.title.setFont(UITools.getFont(Font.PLAIN, 24));
        this.title.setForeground(TEXT);
        this.add(this.title, 0);
    }

    @Override
    protected void paintComponent(Graphics2D g2D) {
        super.paintComponent(g2D);
        g2D.setColor(Color.WHITE);
        g2D.fillRoundRect(0, 0, this.getSize().width, this.getSize().height, 8, 8);
    }

}
