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
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class WndPartGen extends JFrame implements ActionListener {

    // версия класса
    private static final long serialVersionUID = 1L;

    public JFrame frame = new JFrame("Create partition");
    private JLabel rootLabel = new JLabel("OS Root:");
    private JLabel nameLabel = new JLabel("Part Name:");
    private JLabel memLabel = new JLabel("Part Memory:");
    private JLabel portLabel = new JLabel("Part Ports:");
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
    private JButton rootSelectBtn = new JButton("Select");
    private JButton memBtn = new JButton("SetMem");
    private JButton portBtn = new JButton("SetPorts");

    private PartParamContainer paramContainer = new PartParamContainer();

    public WndPartGen() {
        initParamContainer();

        Dimension size = new Dimension();

        partNameField.setText(paramContainer.getPartName());
        size.setSize(200, 30);
        partNameField.setPreferredSize(size);

        rootPathField.setText(paramContainer.getOsRootPath());
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
    	// TODO: read params from XML
        paramContainer.setPartName("partition_4");
        paramContainer.setOsRootPath("/home/user/share/os");

        MemParam mem = new MemParam();
        
        		mem.setCodeMem("2048");
        		mem.setDataMem("2048");
        paramContainer.setMem(mem);
        
        List<PortParam> ports = new ArrayList<>();
        
        paramContainer.setPorts(ports);
    }


    private void osRootHandler() {
        JFileChooser fileChooser = new JFileChooser();
        String osRootPath;
        int userSelection;
        File osRootDir;

        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setDialogTitle("Specify OS root");

        // Setup FileChooser
        osRootPath = rootPathField.getText();
        if (osRootPath != null) {
            osRootDir = new File(osRootPath);
            if (osRootDir.exists()) {
                osRootPath = osRootDir.getAbsolutePath();
            }
            log.info("Start_path " + osRootPath);

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

    private void portHandler() {
        new PortDialog(this);
    }

    private void partCreateHandler() {

        paramContainer.setOsRootPath(rootPathField.getText());
        paramContainer.setPartName(partNameField.getText());

        PartCreator partCreator = new PartCreator(this);

        partCreator.partCreate();

    }

    // event handler, part of ActionListener interface
    @Override
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
            paramContainer.setSystemPartition(sysPartCBtn.isSelected());
        }

    }

    public void setMem(MemParam mem) {

        paramContainer.getMem().setCodeMem(mem.getCodeMem());
        paramContainer.getMem().setDataMem(mem.getDataMem());

    }

    public MemParam getMem() {

        MemParam mem = new MemParam();

        mem.setCodeMem(paramContainer.getMem().getCodeMem());
        mem.setDataMem(paramContainer.getMem().getDataMem());

        return mem;
    }

    public String getPartName() {

        return paramContainer.getPartName();
    }

    public String getRootPath() {

        return paramContainer.getOsRootPath();
    }

    public boolean isSystemPart() {

        return paramContainer.isSystemPartition();
    }

    public int getNumOfPorts() {

        return paramContainer.getPorts().size();
    }

    public void setPort(PortParam portParam) {

        paramContainer.getPorts().add(portParam);
    }

    public List<PortParam> getPorts() {
        //TODO: make copy of ports
        // Disable editing parameters
        return paramContainer.getPorts();
    }

    public PartParamContainer getPC() {
        //TODO: make copy of PC
        // Disable editing parameters
        return paramContainer;
    }

}
