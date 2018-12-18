package network.quant.compoent;

import network.quant.utils.UITools;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InsuranceComponent extends DetailComponent {

    JLabel policyLabel = new JLabel("Policy number");
    JLabel premiumLabel = new JLabel("Premium");
    public JTextField policy = new JTextField(UUID.randomUUID().toString());
    public JTextField premium = new JTextField("0.5 ETH");

    public InsuranceComponent(Dimension dimension) {
        super(dimension);
        this.title.setText("Insurance");

        int componentIndex = 1;

        this.policyLabel.setSize(dimension.width-40, 32);
        this.policyLabel.setLocation(20, 32);
        this.policyLabel.setFont(UITools.getFont(Font.BOLD, 16));
        this.policyLabel.setForeground(TEXT);
        this.policyLabel.setHorizontalAlignment(JLabel.RIGHT);
        this.add(this.policyLabel, componentIndex++);

        this.policy.setSize(dimension.width-40, 32);
        this.policy.setLocation(20, 64);
        this.policy.setFont(UITools.getFont(Font.PLAIN, 16));
        this.policy.setForeground(TEXT);
        this.add(this.policy, componentIndex++);

        this.premiumLabel.setSize(dimension.width-40, 32);
        this.premiumLabel.setLocation(20, 96);
        this.premiumLabel.setFont(UITools.getFont(Font.BOLD, 16));
        this.premiumLabel.setForeground(TEXT);
        this.premiumLabel.setHorizontalAlignment(JLabel.RIGHT);
        this.add(this.premiumLabel, componentIndex++);

        this.premium.setSize(dimension.width-40, 32);
        this.premium.setLocation(20, 128);
        this.premium.setFont(UITools.getFont(Font.PLAIN, 16));
        this.premium.setForeground(TEXT);
        this.add(this.premium, componentIndex++);
    }

}
