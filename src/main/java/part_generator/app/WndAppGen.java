package part_generator.app;

import lombok.extern.slf4j.Slf4j;
import part_generator.MemDialog;
import part_generator.PartCreator;
import part_generator.part_param_container.MemParam;
import part_generator.part_param_container.PartParamContainer;
import part_generator.port_dialog.PortDialog;
import part_generator.part_param_container.PortParam;
import part_generator.ParserXML;

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
	
	private ParserXML parser;
	
	private JPanel line1 = new JPanel();
	private JPanel line2 = new JPanel();
	private JPanel line3 = new JPanel();
	
	private JLabel appLabel = new JLabel("Application path:");
	private JTextField appPathField = new JTextField();
	private JButton appSelectBtn = new JButton("AppSelect");
	
	private JLabel partLabel = new JLabel("Add Partition:");
	private JButton newPartBtn = new JButton("Create");
	private JButton existPartBtn = new JButton("Select");
	
	private JLabel partListLabel = new JLabel("Partition list:");
	private String lislItems[] = {"Empty"};
	private JList partList = new JList(lislItems);
	
	public WndAppGen() {
		
		Dimension size = new Dimension();
		
		//panel.setLayout(null);
		
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 480);
        
        
        size.setSize(200, 30);
        appPathField.setPreferredSize(size);
        
        
        appSelectBtn.addActionListener(this);

        
        //place components
        line1.add(appLabel);
        line1.add(appPathField);
        line1.add(appSelectBtn);
        
        line2.add(partLabel);
        line2.add(newPartBtn);
        line2.add(existPartBtn);
        
        line3.add(partListLabel);
        line3.add(partList);
        
        panel.add(line1);
        panel.add(line2);
        panel.add(line3);
        
        frame.setContentPane(panel);
        frame.setVisible(true);
	}
	
	private void selectApplication() {
		JFileChooser fileChooser = new JFileChooser();
		String appPath;
		File appDir;
		int res;
		
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setDialogTitle("Specify App Directory");
        
        // Setup FileChooser
        appPath = appPathField.getText();
        if (appPath != null) {
            appDir = new File(appPath);
            if (appDir.exists()) {
            	appPath = appDir.getAbsolutePath();
            }
            log.info("Start_path " + appPath);

            fileChooser.setCurrentDirectory(appDir);
        }
        
        res = fileChooser.showOpenDialog(frame);
        
        // Select app folder
        if (res == JFileChooser.APPROVE_OPTION) {
        	appDir = fileChooser.getSelectedFile();

            appPath = appDir.getAbsolutePath();

            appPathField.setText(appPath);
            
            checkAppStruct(appPath);
            //scanForParts();
        }
	}
	
	private void checkAppStruct(String appPath) {
		File appDir;
		File appXML;
		String appName;
		String tmp;
		
		appDir = new File(appPath);
		
		appName = appDir.getName();
		
		tmp = appDir + "/" + appName + ".xml";
		
		log.info(tmp);
		
		appXML = new File(tmp);
		
		if(appXML.exists()) {
			//parse app configuration
			parser = new ParserXML(appXML);
			
			parser.init();
			
			//int nparts = parser.getNumOfParts();
			
			//System.out.println("N Parts = " + nparts);
			
		}else {
			//Do nothing ?
		}
	}
	
	// event handler, part of ActionListener interface
	@Override
	public void actionPerformed(ActionEvent evt) {
		
        if (evt.getActionCommand().equals("AppSelect")) {
        	selectApplication();
        }
		
	}
	
}