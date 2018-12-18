package network.quant.event;

import network.quant.compoent.ApplicationFrame;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplicationMovingEvent extends MouseAdapter implements MouseMotionListener {

    private static ApplicationMovingEvent I;
    @NonNull
    JFrame frame;
    Point screenLocation, mouseLocation;

    @Override
    public void mousePressed(MouseEvent e) {
        this.screenLocation = this.frame.getLocationOnScreen();
        this.mouseLocation = e.getPoint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point point = e.getPoint();
        point.x -= this.mouseLocation.x;
        point.y -= this.mouseLocation.y;
        this.screenLocation.x += point.x;
        this.screenLocation.y += point.y;
        this.frame.setLocation(this.screenLocation);
    }

    public static ApplicationFrame adapter(ApplicationFrame frame) {
        if (null == I) {
            I = new ApplicationMovingEvent(frame);
            frame.addMouseListener(I);
            frame.addMouseMotionListener(I);
        }
        return frame;
    }

}
