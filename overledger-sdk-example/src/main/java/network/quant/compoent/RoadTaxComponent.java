package network.quant.compoent;

import network.quant.utils.UITools;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoadTaxComponent extends DetailComponent {

    JLabel regLabel = new JLabel("Reg Number");
    JLabel annualLabel = new JLabel("Annual Price");
    public JTextField reg = new JTextField("WR18 XSR");
    public JTextField annual = new JTextField("300 XRP");

    public RoadTaxComponent(Dimension dimension) {
        super(dimension);
        this.title.setText("Road Tax");

        int componentIndex = 1;

        this.regLabel.setSize(dimension.width-40, 32);
        this.regLabel.setLocation(20, 32);
        this.regLabel.setFont(UITools.getFont(Font.BOLD, 16));
        this.regLabel.setForeground(TEXT);
        this.regLabel.setHorizontalAlignment(JLabel.RIGHT);
        this.add(this.regLabel, componentIndex++);

        this.reg.setSize(dimension.width-40, 32);
        this.reg.setLocation(20, 64);
        this.reg.setFont(UITools.getFont(Font.PLAIN, 16));
        this.reg.setForeground(TEXT);
        this.add(this.reg, componentIndex++);

        this.annualLabel.setSize(dimension.width-40, 32);
        this.annualLabel.setLocation(20, 96);
        this.annualLabel.setFont(UITools.getFont(Font.BOLD, 16));
        this.annualLabel.setForeground(TEXT);
        this.annualLabel.setHorizontalAlignment(JLabel.RIGHT);
        this.add(this.annualLabel, componentIndex++);

        this.annual.setSize(dimension.width-40, 32);
        this.annual.setLocation(20, 128);
        this.annual.setFont(UITools.getFont(Font.PLAIN, 16));
        this.annual.setForeground(TEXT);
        this.add(this.annual, componentIndex++);
    }

}
