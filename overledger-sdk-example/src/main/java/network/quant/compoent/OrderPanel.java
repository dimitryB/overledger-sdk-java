package network.quant.compoent;

import network.quant.essential.dto.OverledgerTransactionResponse;
import network.quant.mvp.presenter.ContentPresenter;
import network.quant.utils.UITools;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderPanel extends BaseComponent {

    private static final Color TEXT = new Color(38, 31, 99);
    ContentPresenter contentPresenter;

    public OrderPanel(Dimension dimension, ContentPresenter contentPresenter) {
        super(dimension);
        this.contentPresenter = contentPresenter;

        int componentIndex = 0;

        JLabel label = new JLabel("Order");
        label.setSize(dimension.width - 20, 64);
        label.setLocation(10, 10);
        label.setFont(UITools.getFont(Font.PLAIN, 48));
        label.setForeground(TEXT);
        this.add(label, componentIndex++);


    }

    public void loadList(OverledgerTransactionResponse[] writeOverledgerTransactionResponses) {
        int componentIndex = 1;
        for (int i=0; i<writeOverledgerTransactionResponses.length; i++) {
            Button button = new Button(writeOverledgerTransactionResponses[i].getOverledgerTransactionId().toString(), Button.TYPE.CANCEL);
            button.setSize(this.getSize().width-40, 32);
            button.setLocation(20, i * 40 + 80);
            this.add(button, componentIndex++);
            System.out.println(writeOverledgerTransactionResponses[i]);
        }
    }

}
