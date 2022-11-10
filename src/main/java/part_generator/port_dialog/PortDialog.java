package part_generator.port_dialog;

import lombok.extern.slf4j.Slf4j;
import part_generator.PortSetDialog;
import part_generator.app.VerticalLayout;
import part_generator.app.WndPartGen;
import part_generator.part_param_container.PortParam;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


@Slf4j
public class PortDialog extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;
    private static final String DONE = "Done";
    private static final String APPLY = "Apply";

    private final JDialog dialog;
    private final WndPartGen pParent;
    private final JPanel prtPanel;
    JLabel nPortsLabel;
    JTextField numPortsField;
    JPanel prtline1;
    JPanel prtline2;
    JButton prtApplyBtn;
    JButton prtDoneBtn;
    PortParam[] tmpPorts;

    public PortDialog(WndPartGen parent) {
        pParent = parent;
        String nPorts = String.format("%d", pParent.getNumOfPorts());

        dialog = new JDialog(parent.frame, true); // parent, isModal
        prtPanel = new JPanel(new VerticalLayout());
        nPortsLabel = new JLabel("Num of ports");
        numPortsField = new JTextField(nPorts);
        prtline1 = new JPanel();
        prtline2 = new JPanel();
        prtDoneBtn = new JButton(DONE);
        prtApplyBtn = new JButton(APPLY);
        Dimension size = new Dimension();

        prtDoneBtn.addActionListener(this);
        prtApplyBtn.addActionListener(this);

        size.setSize(50, 20);
        numPortsField.setPreferredSize(size);

        dialog.setSize(480, 320);
        dialog.setContentPane(prtPanel);

        updateNumOfPorts();

        dialog.setVisible(true); // blocks until dialog is closed
    }

    private void updateNumOfPorts() {
        int numOfPorts = Integer.parseInt(numPortsField.getText());

        tmpPorts = new PortParam[numOfPorts];

        prtPanel.removeAll();

        prtline1.add(nPortsLabel);
        prtline1.add(numPortsField);
        prtline1.add(prtApplyBtn);

        prtPanel.add(prtline1);

        for (int i = 0; i < numOfPorts; i++) {
            JPanel portLineN = new JPanel();
            JLabel portNameN = new JLabel();
            SetBtn portSetN = new SetBtn(i);

            portNameN.setText("Port_" + String.format("%d", i));
            portSetN.setText("Set");

            portSetN.addActionListener(this);

            portLineN.add(portNameN);
            portLineN.add(portSetN);

            prtPanel.add(portLineN);
        }

        prtPanel.add(prtDoneBtn);

        prtPanel.validate();
        prtPanel.repaint();
    }

    // event handler, part of ActionListener interface
    public void actionPerformed(ActionEvent arg0) {
        // Set num of ports
        if (arg0.getActionCommand().equals(APPLY)) {
            log.info(APPLY);

            updateNumOfPorts();
        }

        // Set ports parameters
        if (arg0.getActionCommand().equals("Set")) {
            log.info("Set");
            SetBtn portSetBtn = (SetBtn) arg0.getSource();
            int portID = portSetBtn.getPortId();

            if (tmpPorts[portID] == null) {
                tmpPorts[portID] = new PortParam();
            }

            new PortSetDialog(pParent, tmpPorts[portID]);
        }

        // Set ports parameters
        if (arg0.getActionCommand().equals(DONE)) {
            log.info(DONE);
            int numOfPorts = Integer.parseInt(numPortsField.getText());

            for (int i = 0; i < numOfPorts; i++) {
                if (tmpPorts[i] != null) {
                    pParent.setPort(tmpPorts[i]);
                }
            }

            dialog.dispose();
        }
    }
}
