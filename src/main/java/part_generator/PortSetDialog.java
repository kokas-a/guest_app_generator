package part_generator;

import lombok.extern.slf4j.Slf4j;
import part_generator.app.VerticalLayout;
import part_generator.app.WndPartGen;
import part_generator.part_param_container.PortParam;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@Slf4j
public class PortSetDialog extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;

    private final PortParam pPort;
    private final JDialog dialog;
    private final JTextField portNameField;
    private final JTextField portMsgNumField;
    private final JTextField portMsgLenField;
    private final JComboBox<String> portDirectionCB;
    private final JComboBox<String> portTypeCB;

    public PortSetDialog(WndPartGen parent, PortParam port) {

        pPort = port;

        String[] portDirItems = {
                "EXT_DIR_INPUT",
                "EXT_DIR_OUTPUT",
        };

        String[] portTypeItems = {
                "PART_QUEUE_PORT",
//		    "PART_SAMPLE_PORT",
        };

        dialog = new JDialog(parent.frame, true); // parent, isModal
        JPanel mPanel = new JPanel(new VerticalLayout());
        JLabel portNameLbl = new JLabel("Port Name:");
        JLabel portTypeLbl = new JLabel("Port Type:");
        JLabel portDirLbl = new JLabel("Port Dir:");
        JLabel portNMsgLbl = new JLabel("Port Msg Num:");
        JLabel portMsgLenLbl = new JLabel("Port Msg Len:");
        portNameField = new JTextField();
        portMsgNumField = new JTextField();
        portMsgLenField = new JTextField();
        JPanel pLine1 = new JPanel();
        JPanel pLine2 = new JPanel();
        JPanel pLine3 = new JPanel();
        JPanel pLine4 = new JPanel();
        JPanel pLine5 = new JPanel();
        JButton mDoneBtn = new JButton("Done");
        portDirectionCB = new JComboBox<>(portDirItems);
        portTypeCB = new JComboBox<>(portTypeItems);
        Dimension size = new Dimension();

        mDoneBtn.addActionListener(this);

        size.setSize(70, 20);
        portNameField.setPreferredSize(size);
        portMsgNumField.setPreferredSize(size);
        portMsgLenField.setPreferredSize(size);

        pLine1.add(portNameLbl);
        pLine1.add(portNameField);

        pLine2.add(portTypeLbl);
        pLine2.add(portTypeCB);

        pLine3.add(portDirLbl);
        pLine3.add(portDirectionCB);

        pLine4.add(portNMsgLbl);
        pLine4.add(portMsgNumField);

        pLine5.add(portMsgLenLbl);
        pLine5.add(portMsgLenField);

        mPanel.add(pLine1);
        mPanel.add(pLine2);
        mPanel.add(pLine3);
        mPanel.add(pLine4);
        mPanel.add(pLine5);
        mPanel.add(mDoneBtn);

        dialog.setSize(480, 320);
        dialog.setContentPane(mPanel);
        dialog.setVisible(true); // blocks until dialog is closed
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        String tmp;

        // Set ports parameters
        if (arg0.getActionCommand().equals("Done")) {
            log.info("Done");

            pPort.setPortName(portNameField.getText());
            pPort.setPortDirection((String)portDirectionCB.getSelectedItem());
            pPort.setPortType((String) portTypeCB.getSelectedItem());

            tmp = portMsgNumField.getText();

            if (tmp.length() == 0) {
                pPort.setMaxMsgNum("0");
            } else {
                pPort.setMaxMsgNum(tmp);
            }

            tmp = portMsgLenField.getText();
            if (tmp.length() == 0) {
                pPort.setMaxMsgLen("0");
            } else {
                pPort.setMaxMsgLen(tmp);
            }

            dialog.dispose();
        }
    }

}
