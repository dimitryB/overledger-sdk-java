package network.quant.compoent;

import javax.swing.*;
import java.awt.*;

public abstract class BaseComponent extends JComponent {

    public BaseComponent(Dimension dimension) {
        this.setComponentSize(dimension);
        this.setLayout(null);
    }

    protected void setComponentSize(Dimension dimension) {
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setMaximumSize(dimension);
        this.setMinimumSize(dimension);
    }

    protected Graphics2D paintBaseComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        return graphics2D;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.paintComponent(this.paintBaseComponent(g));
    }

    protected void paintComponent(Graphics2D g2D) {}

}
