package aaa;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.velocity.app.Velocity;


//Менеджер вертикального расположения компонентов
class VerticalLayout implements LayoutManager {
	private Dimension size = new Dimension();

	// Следующие два метода не используются
	public void addLayoutComponent(String name, Component comp) {
	}

	public void removeLayoutComponent(Component comp) {
	}

	// Метод определения минимального размера для контейнера
	public Dimension minimumLayoutSize(Container c) {
		return calculateBestSize(c);
	}

	// Метод определения предпочтительного размера для контейнера
	public Dimension preferredLayoutSize(Container c) {
		return calculateBestSize(c);
	}

	// Метод расположения компонентов в контейнере
	public void layoutContainer(Container container) {
		// Список компонентов
		Component list[] = container.getComponents();
		int currentY = 5;
		for (int i = 0; i < list.length; i++) {
			// Определение предпочтительного размера компонента
			Dimension pref = list[i].getPreferredSize();
			// Размещение компонента на экране
			list[i].setBounds(5, currentY, pref.width, pref.height);
			// Учитываем промежуток в 5 пикселов
			currentY += 5;
			// Смещаем вертикальную позицию компонента
			currentY += pref.height;
		}
	}

	// Метод вычисления оптимального размера контейнера
	private Dimension calculateBestSize(Container c) {
		// Вычисление длины контейнера
		Component[] list = c.getComponents();
		int maxWidth = 0;
		for (int i = 0; i < list.length; i++) {
			int width = list[i].getWidth();
			// Поиск компонента с максимальной длиной
			if (width > maxWidth)
				maxWidth = width;
		}
		// Размер контейнера в длину с учетом левого отступа
		size.width = maxWidth + 5;
		// Вычисление высоты контейнера
		int height = 0;
		for (int i = 0; i < list.length; i++) {
			height += 5;
			height += list[i].getHeight();
		}
		size.height = height;
		return size;
	}
}

class WndPartGen extends JFrame implements ActionListener {
	// версия класса
	private static final long serialVersionUID = 1L;
	
	JFrame frame = new JFrame("Create partition");
	private JLabel rootLabel = new JLabel ("OS Root:");
	private JLabel nameLabel = new JLabel ("Part Name:");
	private JLabel memLabel = new JLabel ("Part Memory:");
	private JLabel portLabel = new JLabel ("Part Ports:");
	private JCheckBox sysPartCBtn = new JCheckBox("System Partition");
	private JTextField partNameField = new JTextField();
	private JTextField rootPathField = new JTextField();
	private JPanel line1 = new JPanel();
	private JPanel line2 = new JPanel();
	private JPanel line3 = new JPanel();
	private JPanel line4 = new JPanel();
	private JPanel line5 = new JPanel();
	private JPanel panel = new JPanel(new VerticalLayout());
	private JButton createBtn = new JButton("Create");
	private JButton rootSelectBtn= new JButton("Select");
	private JButton memBtn = new JButton("SetMem");
	private JButton portBtn = new JButton("SetPorts");
	
	private PartParamContainer paramContainer = new PartParamContainer();
	
	public WndPartGen() {
		initParamContainer();
		
		Dimension size = new Dimension();
		
		partNameField.setText(paramContainer.partName);
		size.setSize(200, 30);
		partNameField.setPreferredSize(size);
		
		rootPathField.setText(paramContainer.osRootPath);
		size.setSize(400, 30);
		rootPathField.setPreferredSize(size);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 480);
		
		// set event handlers
		createBtn.addActionListener(this); 
		rootSelectBtn.addActionListener(this);
		memBtn.addActionListener(this);
		portBtn.addActionListener(this);
		sysPartCBtn.addActionListener(this);
		
		//place components
		line1.add(rootLabel);
		line1.add(rootPathField);
		line1.add(rootSelectBtn);
		
		line2.add(nameLabel);
		line2.add(partNameField);
		
		line3.add(memLabel);
		line3.add(memBtn);
		
		line4.add(sysPartCBtn);
		
		line5.add(portLabel);
		line5.add(portBtn);
						
		panel.add(line1);
		panel.add(line2);
		panel.add(line3);
		panel.add(line4);
		panel.add(line5);
		
		panel.add(createBtn);
	
		frame.setContentPane(panel);
		frame.setVisible(true);
	}
	
	private void initParamContainer() {
		paramContainer.partName = "partition_4";
		paramContainer.osRootPath = "/home/user/share/os";
		
		paramContainer.mem.codeMem = "2048";
		paramContainer.mem.dataMem = "2048";
	}
	
	
	
	private void osRootHandler() {
		JFileChooser fileChooser = new JFileChooser();
		String osRootPath;
		int userSelection;
		File osRootDir;
		
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setDialogTitle("Specify BolgenOS root");
		
		// Setup FileChooser
		osRootPath = rootPathField.getText();
		if (osRootPath != null) {
			osRootDir = new File(osRootPath);
			if (osRootDir.exists())
				osRootPath = osRootDir.getAbsolutePath();
			System.out.println("Start_path " + osRootPath);

			fileChooser.setCurrentDirectory(osRootDir);
		}
		
		userSelection = fileChooser.showOpenDialog(frame);
		
		// Select OS root folder
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			osRootDir = fileChooser.getSelectedFile();

			osRootPath = osRootDir.getAbsolutePath();

			rootPathField.setText(osRootPath);
		}
		
		/* TODO: make sure that given path contain OS */
	}
	
	private void memHandler() {
		new MemDialog(this);
	}
	
	private void portHandler(){
		new PortDialog(this);
	}
	
	private void partCreateHandler() {	
		
		paramContainer.osRootPath = rootPathField.getText();
		paramContainer.partName = partNameField.getText();
		
		PartCreator partCreator = new PartCreator(this);
		
		partCreator.partCreate();
		
		return;
	}
	
	// event handler, part of ActionListener interface
	public void actionPerformed(ActionEvent arg0) {

		// Create partition with given parameters
		if (arg0.getActionCommand().equals("Create")) {
			partCreateHandler();
		} 
		
		// Select OS root path
		if (arg0.getActionCommand().equals("Select")) {			
			osRootHandler();
		} 
		
		// Set memory parameters
		if (arg0.getActionCommand().equals("SetMem")) {			
			memHandler();
		}
		
		if (arg0.getActionCommand().equals("SetPorts")) {			
			portHandler();
		}
		
		if (arg0.getActionCommand().equals("System Partition")) {			
			paramContainer.systemPartition = sysPartCBtn.isSelected();
		}
		
		return;
	}
	
	public void setMem(MemParam mem) {
		
		paramContainer.mem.codeMem = mem.codeMem;
		paramContainer.mem.dataMem = mem.dataMem;
		
		return;
	}
	
	public MemParam getMem() {
		
		MemParam mem = new MemParam();
		
		mem.codeMem = paramContainer.mem.codeMem;
		mem.dataMem = paramContainer.mem.dataMem;
		
		return mem;
	}
	
	public String getPartName() {
		
		return paramContainer.partName;
	}
	
	public String getRootPath() {
		
		return paramContainer.osRootPath;
	}
	
	public boolean isSystemPart() {
		
		return paramContainer.systemPartition;
	}

	public int getNumOfPorts() {
		
		return paramContainer.ports.size();
	}
	
	public void setPort(PortParam portParam) {

		paramContainer.ports.add(portParam);
		return;
	}
	
	public ArrayList<PortParam> getPorts() {
		//TODO: make copy of ports
		// Disable editing parameters
		return paramContainer.ports;
	}
	
	public PartParamContainer getPC() {
		//TODO: make copy of PC
		// Disable editing parameters
		return paramContainer;
	}
}

public class App 
{
    public static void main( String[] args )
    {
    	Velocity.init();
    	
    	new WndPartGen();
    }
}
