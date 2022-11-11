package part_generator;

import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import part_generator.app.WndPartGen;
import part_generator.part_param_container.MemParam;
import part_generator.part_param_container.PartParamContainer;
import part_generator.part_param_container.PortParam;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class PartCreator {

	private static final String FILE_CREATED = "File created: ";
	private static final String FILE_ALREADY_EXISTS = "File already exists: ";
	private static final String ERROR_OCCURED_AT = "An error occurred at ";
    private static final String FAILED_TO_CREATE_FILE_WRITER = "Failed to create filewriter";
    private static final String PART_NAME = "partName";
    private static final String PATH_DELIMETER = "/";

    private final WndPartGen wndParent;

    public PartCreator(WndPartGen parent) {
        wndParent = parent;
    }

    //todo: возвращаемое методом значение нигде не используется, может сделать его void?
    public boolean partCreate() {
        File partFolder = checkPartFolder(wndParent.getRootPath(), wndParent.getPartName());
        File fileDescr;
		boolean res = false;

		if (partFolder == null) {
			return res;
		}

		String partPath = partFolder.getAbsolutePath();
		String fileFullPath = partPath + PATH_DELIMETER + "Makefile";

		// Makefile
        try {
            fileDescr = new File(fileFullPath);
            if (fileDescr.createNewFile()) {
                log.error(FILE_CREATED + fileFullPath);
            } else {
                log.info(FILE_ALREADY_EXISTS + fileFullPath);
            }

            res = fillMakefile(fileDescr);

        } catch (IOException ioe) {
            log.error(ERROR_OCCURED_AT + fileFullPath, ioe);
            return res;
        }

        // memory.lds
        fileFullPath = partPath + PATH_DELIMETER + "memory.lds";
        try {
            fileDescr = new File(fileFullPath);
            if (fileDescr.createNewFile()) {
                log.info(FILE_CREATED + fileDescr.getAbsolutePath());
            } else {
                log.info(FILE_ALREADY_EXISTS + fileDescr.getAbsolutePath());
            }

            res = fillMemoryLds(fileDescr);

        } catch (IOException ioe) {
            log.error(ERROR_OCCURED_AT + fileDescr.getAbsolutePath(), ioe);
            return res;
        }

        // partition descriptor
        fileFullPath = partPath + PATH_DELIMETER + wndParent.getPartName() + "_descr.c";

        try {
            fileDescr = new File(fileFullPath);

            if (fileDescr.createNewFile()) {
                log.info(FILE_CREATED + fileFullPath);
            } else {
                log.info(FILE_ALREADY_EXISTS + fileFullPath);
            }

            res = fillPartDescr(fileDescr);

        } catch (IOException ioe) {
            log.error(ERROR_OCCURED_AT + fileFullPath, ioe);
            return res;
        }

        // main.c
        fileFullPath = partPath + PATH_DELIMETER + "main.c";

        try {
            fileDescr = new File(fileFullPath);

            if (fileDescr.createNewFile()) {
                log.info(FILE_CREATED + fileFullPath);
            } else {
                log.info(FILE_ALREADY_EXISTS + fileFullPath);
            }

            res = fillMain(fileDescr);

        } catch (IOException ioe) {
            log.error(ERROR_OCCURED_AT + fileFullPath, ioe);
            return res;
        }

        // part_settings.mk
        fileFullPath = partPath + PATH_DELIMETER + "part_settings.mk";

        try {
            fileDescr = new File(fileFullPath);

            if (fileDescr.createNewFile()) {
				log.info(FILE_CREATED + fileFullPath);
            } else {
				log.info(FILE_ALREADY_EXISTS + fileFullPath);
            }

            res = fillPartSettnigs(fileDescr);

        } catch (IOException ioe) {
			log.error(ERROR_OCCURED_AT + fileFullPath, ioe);
            return res;
        }

        // part_config.h
        fileFullPath = partPath + PATH_DELIMETER + "part_config.h";
        try {
            fileDescr = new File(fileFullPath);

            if (fileDescr.createNewFile()) {
				log.info(FILE_CREATED + fileFullPath);
            } else {
				log.info(FILE_ALREADY_EXISTS + fileFullPath);
            }

            res = fillPartConfig(fileDescr);

        } catch (IOException ioe) {
			log.error(ERROR_OCCURED_AT + fileFullPath, ioe);
            return res;
        }

        // part_config.h
        fileFullPath = partPath + PATH_DELIMETER + "part_descr.xml";
        try {
            fileDescr = new File(fileFullPath);

            if (fileDescr.createNewFile()) {
				log.info(FILE_CREATED + fileFullPath);
            } else {
				log.info(FILE_ALREADY_EXISTS + fileFullPath);
            }

            res = fillPartDescrXml(fileDescr);

        } catch (IOException ioe) {
			log.error(ERROR_OCCURED_AT + fileFullPath, ioe);
            return res;
        }

        return res;
    }

    // Currently use standard partition location: "os_root/userspace/partitions/.."

    private File checkPartFolder(String osPath, String partName) {
        String partPath = osPath + "/userspace/partitions/" + partName;
        File partFolder = new File(partPath);

        if (partFolder.exists()) {
            if (!partFolder.isDirectory()) {
                log.error("Failed to create partition directory!");
                partFolder = null;
            }
        } else {
            // true if the directory was created, false otherwise
            if (partFolder.mkdirs()) {
                log.info("Directory is created!");
            } else {
                log.error("Failed to create partition directory!");
                partFolder = null;
            }
        }

        return partFolder;
    }

    private boolean fillMakefile(File fileDescr) {
        VelocityContext context = new VelocityContext();
        StringWriter writer = new StringWriter();
        Template t = Velocity.getTemplate("makefile_template.vm");
        boolean res = false;

        context.put(PART_NAME, wndParent.getPartName());
        context.put("osRoot", wndParent.getRootPath());

        t.merge(context, writer);

        try (FileWriter fileWriter = new FileWriter(fileDescr);) {
            fileWriter.write(writer.toString());
            res = true;

        } catch (IOException ioe) {
			log.error(FAILED_TO_CREATE_FILE_WRITER, ioe);
        }

        return res;
    }

    private boolean fillMemoryLds(File fileDescr) {
        VelocityContext context = new VelocityContext();
        StringWriter writer = new StringWriter();
        Template t = Velocity.getTemplate("memory_template.vm");
        boolean res = false;
        MemParam mem = wndParent.getMem();

        context.put("codeMemSz", mem.getCodeMem());
        context.put("dataMemSz", mem.getDataMem());

        t.merge(context, writer);

        try (FileWriter fileWriter = new FileWriter(fileDescr);) {

            fileWriter.write(writer.toString());
            res = true;

        } catch (IOException ioe) {
            //
			log.error(FAILED_TO_CREATE_FILE_WRITER, ioe);
        }

        return res;
    }

    private boolean fillPartDescr(File fileDescr) {
        VelocityContext context = new VelocityContext();
        StringWriter writer = new StringWriter();
        Template t = Velocity.getTemplate("part_descr_template.vm");
        boolean res = false;

        MemParam mem = wndParent.getMem();

        List<Map<String, String>> portsMap = new ArrayList<>();
        List<PortParam> ports = wndParent.getPorts();

        context.put(PART_NAME, wndParent.getPartName());
        context.put("codeMemSz", mem.getCodeMem());
        context.put("dataMemSz", mem.getDataMem());

        for (PortParam port : ports) {

			Map<String, String> map = new HashMap<>();

            map.put("portName", port.getPortName());

            map.put("portType", port.getPortType());
            map.put("portDirection", port.getPortDirection());
            map.put("maxMsgNum", port.getMaxMsgNum());
            map.put("maxMsgLen", port.getMaxMsgLen());

            portsMap.add(map);
        }

        context.put("portItemList", portsMap);

        t.merge(context, writer);

        try (FileWriter fileWriter = new FileWriter(fileDescr);) {
            fileWriter.write(writer.toString());
            res = true;

        } catch (IOException ioe) {
            log.error(FAILED_TO_CREATE_FILE_WRITER, ioe);
        }

        return res;
    }

    private boolean fillMain(File fileDescr) {
        VelocityContext context = new VelocityContext();
        StringWriter writer = new StringWriter();
        Template t = Velocity.getTemplate("main_template.vm");
        boolean res = false;

        context.put(PART_NAME, wndParent.getPartName());

        t.merge(context, writer);

        try (FileWriter fileWriter = new FileWriter(fileDescr);) {
            fileWriter.write(writer.toString());
            res = true;

        } catch (IOException ioe) {
            log.error(FAILED_TO_CREATE_FILE_WRITER, ioe);
        }

        return res;
    }

    private boolean fillPartSettnigs(File fileDescr) {
        VelocityContext context = new VelocityContext();
        StringWriter writer = new StringWriter();
        Template t = Velocity.getTemplate("part_settings_template.vm");
        String sysPartVal;
        boolean res = false;


        context.put(PART_NAME, wndParent.getPartName());

        if (wndParent.isSystemPart()) {
            sysPartVal = "1";
        } else {
            sysPartVal = "0";
        }

        context.put("sysPart", sysPartVal);

        t.merge(context, writer);

        try (FileWriter fileWriter = new FileWriter(fileDescr)) {
            fileWriter.write(writer.toString());
            res = true;

        } catch (IOException ioe) {
            log.error(FAILED_TO_CREATE_FILE_WRITER, ioe);
        }

        return res;
    }

    private boolean fillPartConfig(File fileDescr) {
        VelocityContext context = new VelocityContext();
        StringWriter writer = new StringWriter();
        Template t = Velocity.getTemplate("part_config_template.vm");
        boolean res = false;

        context.put(PART_NAME, wndParent.getPartName());
        context.put("partNameUC", wndParent.getPartName().toUpperCase());

        t.merge(context, writer);

        try (FileWriter fileWriter = new FileWriter(fileDescr);) {
            fileWriter.write(writer.toString());
            res = true;

        } catch (IOException ioe) {
            log.error(FAILED_TO_CREATE_FILE_WRITER, ioe);
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

        } catch (JAXBException jaxbe) {
            jaxbe.printStackTrace();
        }

        return res;
    }

}
