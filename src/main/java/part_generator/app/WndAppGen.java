package part_generator.app;

import lombok.extern.slf4j.Slf4j;
import part_generator.MemDialog;
import part_generator.PartCreator;
import part_generator.part_param_container.MemParam;
import part_generator.part_param_container.PartParamContainer;
import part_generator.port_dialog.PortDialog;
import part_generator.part_param_container.PortParam;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

@Slf4j
public class WndAppGen extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	public JFrame frame = new JFrame("Create Application");
	private JPanel panel = new JPanel(new VerticalLayout());
	
	private JPanel line1 = new JPanel();
	private JPanel line2 = new JPanel();
	private JPanel line3 = new JPanel();
	
	private JLabel appLabel = new JLabel("Application path:");
	private JTextField appPathField = new JTextField();
	private JButton appSelectBtn = new JButton("Select");
	
	public WndAppGen() {
		
		Dimension size = new Dimension();
		
		//panel.setLayout(null);
		
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 480);
        
        
        size.setSize(200, 30);
        appPathField.setPreferredSize(size);

        
        //place components
        line1.add(appLabel);
        line1.add(appPathField);
        line1.add(appSelectBtn);
        
        panel.add(line1);
        panel.add(line2);
        panel.add(line3);
        
        frame.setContentPane(panel);
        frame.setVisible(true);
	}
	
	
	// event handler, part of ActionListener interface
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}