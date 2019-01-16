package network.quant.compoent;

import network.quant.utils.UITools;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentComponent extends DetailComponent {

    JLabel paymentLabel = new JLabel("Initial Payment");
    JLabel regLabel = new JLabel("Reg Number");
    public JTextField payment = new JTextField("0.4 BTC");
    public JTextField reg = new JTextField("WR18 XSR");
    JLabel contact;
    public File contactFile;

    public PaymentComponent(Dimension dimension) {
        super(dimension);
        this.title.setText("Payment");

        int componentIndex = 1;

        this.paymentLabel.setSize(150, 32);
        this.paymentLabel.setLocation(10, 50);
        this.paymentLabel.setFont(UITools.getFont(Font.BOLD, 16));
        this.paymentLabel.setForeground(TEXT);
        this.paymentLabel.setHorizontalAlignment(JLabel.RIGHT);
        this.add(this.paymentLabel, componentIndex++);

        this.payment.setSize(250, 32);
        this.payment.setLocation(160, 50);
        this.payment.setFont(UITools.getFont(Font.PLAIN, 16));
        this.payment.setForeground(TEXT);
        this.add(this.payment, componentIndex++);

        this.regLabel.setSize(150, 32);
        this.regLabel.setLocation(10, 100);
        this.regLabel.setFont(UITools.getFont(Font.BOLD, 16));
        this.regLabel.setForeground(TEXT);
        this.regLabel.setHorizontalAlignment(JLabel.RIGHT);
        this.add(this.regLabel, componentIndex++);

        this.reg.setSize(250, 32);
        this.reg.setLocation(160, 100);
        this.reg.setFont(UITools.getFont(Font.PLAIN, 16));
        this.reg.setForeground(TEXT);
        this.add(this.reg, componentIndex++);

        this.contact = new JLabel("Drop Contract Here");
        this.contact.setSize(200, 200);
        this.contact.setForeground(TEXT);
        this.contact.setFont(UITools.getFont(Font.PLAIN, 16));
        this.contact.setVerticalAlignment(JLabel.CENTER);
        this.contact.setHorizontalAlignment(JLabel.CENTER);
        this.contact.setLocation(this.getSize().width - 220, 25);
        this.add(this.contact, componentIndex++);

        this.contact.setDropTarget(new DropTarget(
                this.contact,
                DnDConstants.ACTION_COPY_OR_MOVE,
                new DropTargetAdapter() {

                    @Override
                    public void drop(DropTargetDropEvent dtde) {
                        if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                            try {
                                dtde.acceptDrop(dtde.getDropAction());
                                Object transaferData = dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                                if (null != transaferData && transaferData instanceof java.util.List) {
                                    java.util.List fileList = (List) transaferData;
                                    if (!fileList.isEmpty()) {
                                        contactFile = (File)fileList.get(0);
                                        contact.setText(contactFile.getAbsolutePath());
                                        dtde.dropComplete(true);
                                    }
                                }
                            } catch (Exception e) {
                                log.warn("Unable to preform DnD", e);
                            }
                        } else {
                            dtde.rejectDrop();
                        }
                    }

                }
        ));
    }

    @Override
    protected void paintComponent(Graphics2D g2D) {
        super.paintComponent(g2D);
        g2D.setColor(TEXT);
        g2D.drawRoundRect(this.getSize().width - 220, 25, 200, 200, 16, 16);
    }
}
