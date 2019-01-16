package network.quant.compoent;

import network.quant.event.ApplicationMovingEvent;

import javax.swing.*;
import java.awt.*;

public class ApplicationFrame extends JFrame {

    private static final Color TRANSPARENT = new Color(0, 0, 0, 0);

    private ApplicationFrame(BaseComponent rootPanel) {
        this.setUndecorated(true);
        this.setBackground(TRANSPARENT);
        this.setContentPane(rootPanel);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    public static ApplicationFrame newInstance(BaseComponent rootPanel) {
        return ApplicationMovingEvent.adapter(new ApplicationFrame(rootPanel));
    }

}
