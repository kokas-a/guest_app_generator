package aaa;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PartCreator {
	
	WndPartGen wndParent = null;
	
	private File checkPartFolder(String osPath, String partName) {
		
		String partPath;
		File partFolder;
		
		partPath = osPath;
		partPath += "/userspace/partitions/";
		partPath += partName;
		
		partFolder = new File(partPath);
		
		if(partFolder.exists() ) {
			if(!partFolder.isDirectory()) {
				System.out.println("Failed to create partition directory!");
				partFolder = null;
			}
		}else {
			// true if the directory was created, false otherwise
	        if (partFolder.mkdirs()) {
	            System.out.println("Directory is created!");
	        } else {
	            System.out.println("Failed to create partition directory!");
	            partFolder = null;
	        }
		}
		
		return partFolder;
	}
	
	private boolean fillMakefile(File fileDescr) {
		
		VelocityContext context = null;
		StringWriter writer = null;
		Template t = null;
		FileWriter fileWriter = null;
		boolean res = false;
		
		writer = new StringWriter();
		context = new VelocityContext();
			
		t = Velocity.getTemplate("makefile_template.vm");
		
		context.put("partName", wndParent.getPartName());
		context.put("osRoot", wndParent.getRootPath());
		
		t.merge( context, writer );
		
		try {
			fileWriter = new FileWriter(fileDescr);
			fileWriter.write(writer.toString());
			fileWriter.close();
			res = true;
			
		} catch (IOException e) {
			// 
			System.out.println("Failed to create filewriter");
			e.printStackTrace();
		}		
		
		return res;	
	}
	
	private boolean fillMemoryLds(File fileDescr) {
		
		VelocityContext context = null;
		StringWriter writer = null;
		Template t = null;
		FileWriter fileWriter = null;
		boolean res = false;
		MemParam mem = wndParent.getMem();
		
		writer = new StringWriter();
		context = new VelocityContext();	
			
		t = Velocity.getTemplate("memory_template.vm");
		
		context.put("codeMemSz", mem.codeMem);
		context.put("dataMemSz", mem.dataMem);
		
		t.merge( context, writer );
		
		try {
			fileWriter = new FileWriter(fileDescr);
			fileWriter.write(writer.toString());
			fileWriter.close();
			res = true;
			
		} catch (IOException e) {
			// 
			System.out.println("Failed to create filewriter");
			e.printStackTrace();
		}		
		
		return res;	
	}
	
	private boolean fillPartDescr(File fileDescr) {
		VelocityContext context = null;
		StringWriter writer = null;
		Template t = null;
		FileWriter fileWriter = null;
		boolean res = false;
		PortParam portParam = null;
		 Map<String, String> map = null;
		
		writer = new StringWriter();
		context = new VelocityContext();
		MemParam mem = wndParent.getMem();
		
		ArrayList<Map<String, String>> portsMap = new ArrayList<Map<String, String>>();
		ArrayList<PortParam> ports = wndParent.getPorts();
		
		t = Velocity.getTemplate("part_descr_template.vm");
		
		context.put("partName", wndParent.getPartName());
		context.put("codeMemSz", mem.codeMem);
		context.put("dataMemSz", mem.dataMem);
		
		for(int i = 0; i < ports.size(); i++) {
			
			portParam = ports.get(i);
			map = new HashMap<String, String>();
			
	        map.put("portName", portParam.portName);
	        
	        map.put("portType", portParam.portType);
	        map.put("portDirection", portParam.portDirection);
	        map.put("maxMsgNum", portParam.maxMsgNum);
	        map.put("maxMsgLen", portParam.maxMsgLen);
	       
	        portsMap.add(map);
		}
		
		context.put("portItemList", portsMap);
	 
		t.merge( context, writer );
		
		try {
			fileWriter = new FileWriter(fileDescr);
			fileWriter.write(writer.toString());
			fileWriter.close();
			res = true;
			
		} catch (IOException e) {
			// 
			System.out.println("Failed to create filewriter");
			e.printStackTrace();
		}		
		
		return res;	
	}
	
	private boolean fillMain(File fileDescr) {
		VelocityContext context = null;
		StringWriter writer = null;
		Template t = null;
		FileWriter fileWriter = null;
		boolean res = false;
		
		writer = new StringWriter();
		context = new VelocityContext();
			
		t = Velocity.getTemplate("main_template.vm");
		
		context.put("partName", wndParent.getPartName());
		
		t.merge( context, writer );
		
		try {
			fileWriter = new FileWriter(fileDescr);
			fileWriter.write(writer.toString());
			fileWriter.close();
			res = true;
			
		} catch (IOException e) {
			// 
			System.out.println("Failed to create filewriter");
			e.printStackTrace();
		}		
		
		return res;	
	}
	
	private boolean fillPartSettnigs(File fileDescr) {
		VelocityContext context = null;
		StringWriter writer = null;
		Template t = null;
		FileWriter fileWriter = null;
		String sysPartVal;
		boolean res = false;
		
		writer = new StringWriter();
		context = new VelocityContext();
			
		t = Velocity.getTemplate("part_settings_template.vm");
		
		context.put("partName", wndParent.getPartName());
		
		if(wndParent.isSystemPart() == true) {
			sysPartVal = "1";
		}else {
			sysPartVal = "0";
		}
		
		context.put("sysPart", sysPartVal);
		
		t.merge( context, writer );
		
		try {
			fileWriter = new FileWriter(fileDescr);
			fileWriter.write(writer.toString());
			fileWriter.close();
			res = true;
			
		} catch (IOException e) {
			// 
			System.out.println("Failed to create filewriter");
			e.printStackTrace();
		}		
		
		return res;	
	}
	
	private boolean fillPartConfig(File fileDescr) {
		VelocityContext context = null;
		StringWriter writer = null;
		Template t = null;
		FileWriter fileWriter = null;
		boolean res = false;
		
		writer = new StringWriter();
		context = new VelocityContext();
			
		t = Velocity.getTemplate("part_config_template.vm");
		
		context.put("partName", wndParent.getPartName());
		context.put("partNameUC", wndParent.getPartName().toUpperCase());

		t.merge( context, writer );
		
		try {
			fileWriter = new FileWriter(fileDescr);
			fileWriter.write(writer.toString());
			fileWriter.close();
			res = true;
			
		} catch (IOException e) {
			// 
			System.out.println("Failed to create filewriter");
			e.printStackTrace();
		}		
		
		return res;	
	}	
	
	private boolean fillPartDescrXml(File fileDescr) {
		boolean res = true;
		
		PartParamContainer privParamContainer = wndParent.getPC();
		
		try {
	        JAXBContext jaxbContext = JAXBContext.newInstance(PartParamContainer.class);
	        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

	        // output pretty printed
	        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	        jaxbMarshaller.marshal(privParamContainer, fileDescr);

	    } catch (JAXBException e) {
	        e.printStackTrace();
	    }
	
		return res;
	}
	
	public boolean partCreate() {
		
		File partFolder;
		File fileDescr;
		String partPath;
		String fileFullPath;
		boolean res = false;
		
		partFolder = checkPartFolder(wndParent.getRootPath(), 
				wndParent.getPartName());
		if(partFolder == null) {
			return res;
		}
		
		partPath = partFolder.getAbsolutePath();
		
		// Makefile
		fileFullPath = partPath + "/" + "Makefile";
		try {
			fileDescr = new File(fileFullPath);
			if (fileDescr.createNewFile()) {
				System.out.println("File created: " + fileFullPath);
			} else {
				System.out.println("File already exists: " + fileFullPath);
			}
			
			res = fillMakefile(fileDescr);
			
		} catch (IOException e) {
			System.out.println("An error occurred at " + fileFullPath);
			e.printStackTrace();
			
			return res;
		}
		
		// memory.lds
		fileFullPath = partPath + "/" + "memory.lds";
		try {
			fileDescr = new File(fileFullPath);
			if (fileDescr.createNewFile()) {
				System.out.println("File created: " + fileDescr.getAbsolutePath());
			} else {
				System.out.println("File already exists: " + fileDescr.getAbsolutePath());
			}
			
			res = fillMemoryLds(fileDescr);
			
		} catch (IOException e) {
			System.out.println("An error occurred at " + fileDescr.getAbsolutePath());
			e.printStackTrace();
			
			return res;
		}
		
		// partition descriptor
		fileFullPath = partPath;
		fileFullPath += "/";
		fileFullPath += wndParent.getPartName();
		fileFullPath += "_descr.c";
		try {
			
			fileDescr = new File(fileFullPath);
			if (fileDescr.createNewFile()) {
				System.out.println("File created: " + fileFullPath);
			} else {
				System.out.println("File already exists: " + fileFullPath);
			}
			
			res = fillPartDescr(fileDescr);
			
		} catch (IOException e) {
			System.out.println("An error occurred at " + fileFullPath);
			e.printStackTrace();
			
			return res;
		}
		
		// main.c
		fileFullPath = partPath + "/" + "main.c";
		try {
		
			fileDescr = new File(fileFullPath);
			if (fileDescr.createNewFile()) {
				System.out.println("File created: " + fileFullPath);
			} else {
				System.out.println("File already exists: " + fileFullPath);
			}
			
			res = fillMain(fileDescr);
			
		} catch (IOException e) {
			System.out.println("An error occurred at " + fileFullPath);
			e.printStackTrace();
			
			return res;
		}
		
		// part_settings.mk
		fileFullPath = partPath + "/" + "part_settings.mk";
		try {
			
			fileDescr = new File(fileFullPath);
			if (fileDescr.createNewFile()) {
				System.out.println("File created: " + fileFullPath);
			} else {
				System.out.println("File already exists: " + fileFullPath);
			}
			
			res = fillPartSettnigs(fileDescr);
			
		} catch (IOException e) {
			System.out.println("An error occurred at " + fileFullPath);
			e.printStackTrace();
			
			return res;
		}
		
		// part_config.h
		fileFullPath = partPath + "/" + "part_config.h";
		try {
			
			fileDescr = new File(fileFullPath);
			if (fileDescr.createNewFile()) {
				System.out.println("File created: " + fileFullPath);
			} else {
				System.out.println("File already exists: " + fileFullPath);
			}
			
			res = fillPartConfig(fileDescr);
			
		} catch (IOException e) {
			System.out.println("An error occurred at " + fileFullPath);
			e.printStackTrace();
			
			return res;
		}
		
		// part_config.h
		fileFullPath = partPath + "/" + "part_descr.xml";
		try {
			
			fileDescr = new File(fileFullPath);
			if (fileDescr.createNewFile()) {
				System.out.println("File created: " + fileFullPath);
			} else {
				System.out.println("File already exists: " + fileFullPath);
			}
			
			res = fillPartDescrXml(fileDescr);
			
		} catch (IOException e) {
			System.out.println("An error occurred at " + fileFullPath);
			e.printStackTrace();
			
			return res;
		}
		
		return res;
	}
	
	// Currently use standard partition location: "os_root/userspace/partitions/.."
	public PartCreator(WndPartGen parent) {
		
		wndParent = parent;

	}
}
