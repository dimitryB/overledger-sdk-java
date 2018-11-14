package network.quant.compoent;

import network.quant.mvp.presenter.ContentPresenter;
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
public class SettingsPanel extends BaseComponent {

    private static final Color TEXT = new Color(38, 31, 99);
    ContentPresenter contentPresenter;
    JLabel bpiKeyLabel = new JLabel("BPI Key");
    JLabel mappIdLabel = new JLabel("Mapp Id");
    JLabel writeLabel = new JLabel("Write Endpoint");
    JLabel readByMappIdLabel = new JLabel("Read by MappID Endpoint");
    JLabel readByIdLabel = new JLabel("Read by ID Endpoint");
    JLabel readByHashLabel = new JLabel("Read by Hash Endpoint");
    public JTextField bpiKeyField = new JTextField();
    public JTextField mappIdField = new JTextField();
    public JTextField writeField = new JTextField();
    public JTextField readByMappIdField = new JTextField();
    public JTextField readByIdField = new JTextField();
    public JTextField readByHashField = new JTextField();
    network.quant.compoent.Button okButton = new network.quant.compoent.Button("OK", network.quant.compoent.Button.TYPE.OK);
    network.quant.compoent.Button cancelButton = new network.quant.compoent.Button("CANCEL", Button.TYPE.CANCEL);

    public SettingsPanel(Dimension dimension, ContentPresenter contentPresenter) {
        super(dimension);
        this.contentPresenter = contentPresenter;
        this.setDropTarget(new DropTarget(
                this,
                DnDConstants.ACTION_COPY_OR_MOVE,
                new DropTargetAdapter() {

                    @Override
                    public void drop(DropTargetDropEvent dtde) {
                        if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                            try {
                                dtde.acceptDrop(dtde.getDropAction());
                                Object transaferData = dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                                if (null != transaferData && transaferData instanceof List) {
                                    List fileList = (List) transaferData;
                                    if (!fileList.isEmpty()) {
                                        contentPresenter.loadSettingsPropertiesFromFile((File)fileList.get(0));
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

        int componentIndex = 0;

        JLabel label = new JLabel("Settings");
        label.setSize(dimension.width - 20, 64);
        label.setLocation(10, 10);
        label.setFont(UITools.getFont(Font.PLAIN, 48));
        label.setForeground(TEXT);
        this.add(label, componentIndex++);

        this.bpiKeyLabel.setSize(300, 32);
        this.bpiKeyLabel.setLocation(10, 110);
        this.bpiKeyLabel.setFont(UITools.getFont(Font.BOLD, 16));
        this.bpiKeyLabel.setForeground(TEXT);
        this.add(this.bpiKeyLabel, componentIndex++);

        this.bpiKeyField.setSize(dimension.width - 220, 32);
        this.bpiKeyField.setLocation(310, 110);
        this.bpiKeyField.setFont(UITools.getFont(Font.PLAIN, 16));
        this.bpiKeyField.setForeground(TEXT);
        this.add(this.bpiKeyField, componentIndex++);

        this.mappIdLabel.setSize(300, 32);
        this.mappIdLabel.setLocation(10, 150);
        this.mappIdLabel.setFont(UITools.getFont(Font.BOLD, 16));
        this.mappIdLabel.setForeground(TEXT);
        this.add(this.mappIdLabel, componentIndex++);

        this.mappIdField.setSize(dimension.width - 220, 32);
        this.mappIdField.setLocation(310, 150);
        this.mappIdField.setFont(UITools.getFont(Font.PLAIN, 16));
        this.mappIdField.setForeground(TEXT);
        this.add(this.mappIdField, componentIndex++);

        this.writeLabel.setSize(300, 32);
        this.writeLabel.setLocation(10, 230);
        this.writeLabel.setFont(UITools.getFont(Font.BOLD, 16));
        this.writeLabel.setForeground(TEXT);
        this.add(this.writeLabel, componentIndex++);

        this.writeField.setSize(dimension.width - 220, 32);
        this.writeField.setLocation(310, 230);
        this.writeField.setFont(UITools.getFont(Font.PLAIN, 16));
        this.writeField.setForeground(TEXT);
        this.add(this.writeField, componentIndex++);

        this.readByMappIdLabel.setSize(300, 32);
        this.readByMappIdLabel.setLocation(10, 270);
        this.readByMappIdLabel.setFont(UITools.getFont(Font.BOLD, 16));
        this.readByMappIdLabel.setForeground(TEXT);
        this.add(this.readByMappIdLabel, componentIndex++);

        this.readByMappIdField.setSize(dimension.width - 220, 32);
        this.readByMappIdField.setLocation(310, 270);
        this.readByMappIdField.setFont(UITools.getFont(Font.PLAIN, 16));
        this.readByMappIdField.setForeground(TEXT);
        this.add(this.readByMappIdField, componentIndex++);

        this.readByIdLabel.setSize(300, 32);
        this.readByIdLabel.setLocation(10, 310);
        this.readByIdLabel.setFont(UITools.getFont(Font.BOLD, 16));
        this.readByIdLabel.setForeground(TEXT);
        this.add(this.readByIdLabel, componentIndex++);

        this.readByIdField.setSize(dimension.width - 220, 32);
        this.readByIdField.setLocation(310, 310);
        this.readByIdField.setFont(UITools.getFont(Font.PLAIN, 16));
        this.readByIdField.setForeground(TEXT);
        this.add(this.readByIdField, componentIndex++);

        this.readByHashLabel.setSize(300, 32);
        this.readByHashLabel.setLocation(10, 350);
        this.readByHashLabel.setFont(UITools.getFont(Font.BOLD, 16));
        this.readByHashLabel.setForeground(TEXT);
        this.add(this.readByHashLabel, componentIndex++);

        this.readByHashField.setSize(dimension.width - 220, 32);
        this.readByHashField.setLocation(310, 350);
        this.readByHashField.setFont(UITools.getFont(Font.PLAIN, 16));
        this.readByHashField.setForeground(TEXT);
        this.add(this.readByHashField, componentIndex++);

        this.okButton.setSize(150, 48);
        this.okButton.setLocation(dimension.width - 150, dimension.height - 50);
        this.okButton.addActionListener((event) -> {
            this.contentPresenter.onGotoMainWithUpdate(
                    this.bpiKeyField.getText(),
                    this.mappIdField.getText(),
                    this.writeField.getText(),
                    this.readByMappIdField.getText(),
                    this.readByIdField.getText(),
                    this.readByHashField.getText()
            );
        });
        this.add(this.okButton, componentIndex++);

        this.cancelButton.setSize(150, 48);
        this.cancelButton.setLocation(dimension.width - 320, dimension.height - 50);
        this.cancelButton.addActionListener((event) -> {
            this.contentPresenter.onGotoMain();
        });
        this.add(this.cancelButton, componentIndex++);
    }

}
