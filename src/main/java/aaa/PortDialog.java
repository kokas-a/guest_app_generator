package aaa;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


class SetBtn extends JButton{

	private int portId;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SetBtn(int relPortId) {
		portId = relPortId;
		new JButton();
	}
	
	public int getRelatedPort() {
		return portId;
	}
}

public class PortDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private JDialog dialog = null;
	private WndPartGen pParent = null;
	private JPanel prtPanel = null;
	JLabel nPortsLabel = null;
	JTextField numPortsField = null;
	JPanel prtline1 = null;
	JPanel prtline2 = null;
	JButton prtApplyBtn = null;
	JButton prtDoneBtn = null;
	PortParam[] tmpPorts;
			
	public PortDialog(WndPartGen parent){
		pParent = parent;
		String nPorts = String.format("%d", pParent.getNumOfPorts());
		
		dialog = new JDialog(parent.frame, true); // parent, isModal
		prtPanel = new JPanel(new VerticalLayout());
		nPortsLabel = new JLabel ("Num of ports");
		numPortsField = new JTextField (nPorts);
		prtline1 = new JPanel();
		prtline2 = new JPanel();
		prtDoneBtn = new JButton("Done");
		prtApplyBtn = new JButton("Apply");
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
		int numOfPorts = 0;
		JPanel portLineN = null;
		JLabel portNameN = null;
		SetBtn portSetN = null;
		
		numOfPorts = Integer.parseInt(numPortsField.getText());
		
		tmpPorts = new PortParam[numOfPorts];
		
		prtPanel.removeAll();
		
		prtline1.add(nPortsLabel);
		prtline1.add(numPortsField);
		prtline1.add(prtApplyBtn);
		
		prtPanel.add(prtline1);
		
		for(int i = 0; i < numOfPorts; i++) {
			portLineN = new JPanel();
			portNameN = new JLabel();
			portSetN = new SetBtn(i);
			
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
		if (arg0.getActionCommand().equals("Apply")) {			
			System.out.println("Apply");
			
			updateNumOfPorts();
		}
		
		// Set ports parameters
		if (arg0.getActionCommand().equals("Set")) {			
			SetBtn portSetBtn = null;
			int portID;
			
			System.out.println("Set");
			
			portSetBtn = (SetBtn) arg0.getSource();
			portID = portSetBtn.getRelatedPort();
			
			if(tmpPorts[portID] == null) {
				tmpPorts[portID] = new PortParam();	
			}			
			
			new PortSetDialog(pParent, tmpPorts[portID]);
		}
			
		// Set ports parameters
		if (arg0.getActionCommand().equals("Done")) {	
			int numOfPorts = 0;
			
			System.out.println("Done");
			
			numOfPorts = Integer.parseInt(numPortsField.getText());
			
			for(int i = 0; i < numOfPorts; i++) {
				if(tmpPorts[i] != null) {
					pParent.setPort(tmpPorts[i]);
				}
			}
			
			dialog.dispose();
		}
	}
}


