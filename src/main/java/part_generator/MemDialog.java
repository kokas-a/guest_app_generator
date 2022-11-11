package part_generator;

import part_generator.app.VerticalLayout;
import part_generator.app.WndPartGen;
import part_generator.part_param_container.MemParam;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MemDialog extends JDialog implements ActionListener {
	
	private static final long serialVersionUID = 1L;

	private final JDialog dialog;
	private final WndPartGen pParent;
	private final JTextField codeField;
	private final JTextField dataField;
	
	public MemDialog(WndPartGen parent){
		
		pParent = parent;
		MemParam mem = pParent.getMem();
		
		dialog = new JDialog(parent.frame, true); // parent, isModal
		JPanel mPanel = new JPanel(new VerticalLayout());
		JLabel codeLabel = new JLabel ("Code Memory (KB):");
		JLabel dataLabel = new JLabel ("Data Memory (KB):");
		codeField = new JTextField (mem.getCodeMem());
		dataField = new JTextField(mem.getDataMem());
		JPanel mLine1 = new JPanel();
		JPanel mLine2 = new JPanel();
		JButton mDoneBtn = new JButton("Done");
		Dimension size = new Dimension();
		
		mDoneBtn.addActionListener(this);
		
		size.setSize(70, 20);
		codeField.setPreferredSize(size);
		dataField.setPreferredSize(size);
		
		mLine1.add(codeLabel);
		mLine1.add(codeField);
		
		mLine2.add(dataLabel);
		mLine2.add(dataField);
		
		mPanel.add(mLine1);
		mPanel.add(mLine2);
		mPanel.add(mDoneBtn);
		
		dialog.setSize(480, 320);
		dialog.setContentPane(mPanel);
		dialog.setVisible(true); // blocks until dialog is closed
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		MemParam mem = new MemParam();
		
		// Set memory parameters
		if ("Done".equals(arg0.getActionCommand())) {
			mem.setCodeMem(codeField.getText());
			mem.setDataMem(dataField.getText());
			
			//TODO: Check entering values
			pParent.setMem(mem);
			dialog.dispose();
		}
	}

}
