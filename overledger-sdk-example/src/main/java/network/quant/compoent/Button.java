package network.quant.compoent;

import network.quant.utils.UITools;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Button extends JButton {

    public enum TYPE {

        OK, CANCEL, NORMAL

    }

    private static final Color OK_COLOR = new Color(38, 31, 99);
    private static final Color NORMAL_COLOR = new Color(59, 222, 200);
    private static final Color CANCEL_COLOR = Color.WHITE;
    TYPE type;
    JLabel text;

    public Button(String name, TYPE type) {
        this.type = type;
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));

        this.text = new JLabel(name);
        this.text.setHorizontalAlignment(SwingConstants.CENTER);
        this.text.setVerticalAlignment(SwingConstants.CENTER);
        this.text.setForeground(TYPE.OK.equals(this.type) ? CANCEL_COLOR : OK_COLOR);
        this.text.setFont(UITools.getFont(Font.BOLD, 16));
    }

    @Override
    public void setSize(Dimension d) {
        super.setSize(d);
        this.text.setSize(d);
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        this.text.setSize(width, height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (TYPE.OK.equals(this.type)) {
            graphics2D.setColor(OK_COLOR);
        } else if (TYPE.NORMAL.equals(this.type)) {
            graphics2D.setColor(NORMAL_COLOR);
        } else {
            graphics2D.setColor(CANCEL_COLOR);
        }
        graphics2D.fillRect(0, 0, this.getSize().width, this.getSize().height);
        if (TYPE.OK.equals(this.type) || TYPE.NORMAL.equals(this.type)) {
            graphics2D.setColor(CANCEL_COLOR);
        } else {
            graphics2D.setColor(OK_COLOR);
        }
        this.text.paint(graphics2D);
    }
}
