package aaa;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

class MemParam{
	public String dataMem;
	public String codeMem;
}

class PortParam{
	public String portName;
	public String portType;
	public String portDirection;
	public String maxMsgNum;
	public String maxMsgLen;
}

@XmlRootElement
public class PartParamContainer {
	
	@XmlElement
	public String         partName;
	@XmlElement
	public boolean        systemPartition;
	@XmlElement
	public MemParam       mem = new MemParam();
	@XmlElement
	ArrayList<PortParam>  ports = new ArrayList<PortParam>();
	//ArrayList<Map<String, String>> ports = new ArrayList<Map<String, String>>();
	
	public String         osRootPath;

}
