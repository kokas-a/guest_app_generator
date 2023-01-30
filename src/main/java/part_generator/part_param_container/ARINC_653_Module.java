package part_generator.part_param_container;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

class ErrorIDLevel {
	private String errorIdentifier;
	private String errorLevel;
	private String errorCode;

	public ErrorIDLevel() {
		super();
	}

	@XmlAttribute(name = "ErrorIdentifier")
	public String getErrorIdentifier() {
		return errorIdentifier;
	}

	public void setErrorIdentifier(String errorIdentifier) {
		this.errorIdentifier = errorIdentifier;
	}

	@XmlAttribute(name = "ErrorLevel")
	public String getErrorLevel() {
		return errorLevel;
	}

	public void setErrorLevel(String errorLevel) {
		this.errorLevel = errorLevel;
	}

	@XmlAttribute(name = "ErrorCode")
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}

class SystemStateEntry {
	private String systemState;
	private List<ErrorIDLevel> errorIdLevels;
	private List<ErrorIDAction> errorIdActions;

	public SystemStateEntry() {
		super();
	}

	@XmlAttribute(name = "SystemState")
	public String getSystemState() {
		return systemState;
	}

	public void setSystemState(String systemState) {
		this.systemState = systemState;
	}

	@XmlElement(name="Error_ID_Level")
	public List<ErrorIDLevel> getErrorIDLevels() {
		return errorIdLevels;
	}

	public void setErrorIDLevels(List<ErrorIDLevel> errorIDLevels) {
		this.errorIdLevels = errorIDLevels;
	}

	@XmlElement(name="Error_ID_Action")
	public List<ErrorIDAction> getErrorIDActions() {
		return errorIdActions;
	}

	public void setErrorIDActions(List<ErrorIDAction> errorIDActions) {
		this.errorIdActions = errorIDActions;
	}

}

class SystemHmTable {
	private List<SystemStateEntry> systemStateEntries;

	public SystemHmTable() {
		super();
	}

	@XmlElement(name="System_State_Entry")
	public List<SystemStateEntry> getSystemStateEntries() {
		return systemStateEntries;
	}

	public void setSystemStateEntries(List<SystemStateEntry> systemStateEntries) {
		this.systemStateEntries = systemStateEntries;
	}

}

class ErrorIDAction {
	private String errorIdentifier;

	private String action;

	public ErrorIDAction() {super();}

	@XmlAttribute(name = "ErrorIdentifier")
	public String getErrorIdentifier() {return errorIdentifier;}

	public void setErrorIdentifier(String errorIdentifier) {
		this.errorIdentifier = errorIdentifier;
	}

	@XmlAttribute(name = "Action")
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}

class ModuleHMTable {
	private List<SystemStateEntry> systemStateEntries;

	public ModuleHMTable() {super();}

	@XmlElement(name="System_State_Entry")
	public List<SystemStateEntry> getSystemStateEntries() {
		return systemStateEntries;
	}

	public void setSystemStateEntries(List<SystemStateEntry> systemStateEntries) {
		this.systemStateEntries = systemStateEntries;
	}

}

class QueuingPort {
	private String name;
	private int maxNbMessages;
	private int maxMessageSize;
	private String direction;
	private String redir;

	public QueuingPort() {
		super();
	}

	@XmlAttribute(name = "Name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlAttribute(name = "MaxNbMessages")
	public int getMaxNbMessages() {
		return maxNbMessages;
	}

	public void setMaxNbMessages(int maxNbMessages) {
		this.maxNbMessages = maxNbMessages;
	}

	@XmlAttribute(name = "MaxMessageSize")
	public int getMaxMessageSize() {
		return maxMessageSize;
	}

	public void setMaxMessageSize(int maxMessageSize) {
		this.maxMessageSize = maxMessageSize;
	}

	@XmlAttribute(name = "Direction")
	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	@XmlAttribute(name = "Redir")
	public String getRedir() {
		return redir;
	}

	public void setRedir(String redir) {
		this.redir = redir;
	}
}

class Partition {
	private String entryPoint;
	private int partitionIdentifier;
	private String criticality;
	private String partitionName;
	private boolean systemPartition;
	private int partitionMinPrio;
	private boolean partitionPeriodStart;

	private List<QueuingPort> queuingPorts;
	private List<SamplingPort> samplingPorts;

	public Partition() {super();}

	@XmlAttribute(name = "EntryPoint")
	public String getEntryPoint() {
		return entryPoint;
	}

	public void setEntryPoint(String entryPoint) {
		this.entryPoint = entryPoint;
	}

	@XmlAttribute(name = "PartitionIdentifier")
	public int getPartitionIdentifier() {
		return partitionIdentifier;
	}

	public void setPartitionIdentifier(int partitionIdentifier) {
		this.partitionIdentifier = partitionIdentifier;
	}

	@XmlAttribute(name = "Criticality")
	public String getCriticality() {
		return criticality;
	}

	public void setCriticality(String criticality) {
		this.criticality = criticality;
	}

	@XmlAttribute(name = "PartitionName")
	public String getPartitionName() {
		return partitionName;
	}

	public void setPartitionName(String partitionName) {
		this.partitionName = partitionName;
	}

	@XmlAttribute(name = "SystemPartition")
	public boolean isSystemPartition() {
		return systemPartition;
	}

	public void setSystemPartition(boolean systemPartition) {
		this.systemPartition = systemPartition;
	}

	@XmlAttribute(name = "PartitionMinPrio")
	public int getPartitionMinPrio() {
		return partitionMinPrio;
	}

	public void setPartitionMinPrio(int partitionMinPrio) {
		this.partitionMinPrio = partitionMinPrio;
	}

	@XmlAttribute(name = "PartitionPeriodStart")
	public boolean isPartitionPeriodStart() {
		return partitionPeriodStart;
	}

	public void setPartitionPeriodStart(boolean partitionPeriodStart) {
		this.partitionPeriodStart = partitionPeriodStart;
	}

	@XmlElement(name="Queuing_Port")
	public List<QueuingPort> getQueuingPorts() {
		return queuingPorts;
	}

	public void setQueuingPorts(List<QueuingPort> queuingPorts) {
		this.queuingPorts = queuingPorts;
	}

	@XmlElement(name="Sampling_Port")
	public List<SamplingPort> getSamplingPorts() {
		return samplingPorts;
	}

	public void setSamplingPorts(List<SamplingPort> samplingPorts) {
		this.samplingPorts = samplingPorts;
	}

}

class SamplingPort {
	private double refreshRateSeconds;
	private String direction;
	private int maxMessageSize;
	private String Name;

	public SamplingPort() {super();}

	@XmlAttribute(name = "RefreshRateSeconds")
	public double getRefreshRateSeconds() {
		return refreshRateSeconds;
	}

	public void setRefreshRateSeconds(double refreshRateSeconds) {
		this.refreshRateSeconds = refreshRateSeconds;
	}

	@XmlAttribute(name = "Direction")
	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	@XmlAttribute(name = "MaxMessageSize")
	public int getMaxMessageSize() {
		return maxMessageSize;
	}

	public void setMaxMessageSize(int maxMessageSize) {
		this.maxMessageSize = maxMessageSize;
	}


}

class MemoryRequirements {
	private String sizeBytes;
	private String type;

	public MemoryRequirements() {super();}

	@XmlAttribute(name = "SizeBytes")
	public String getSizeBytes() {
		return sizeBytes;
	}

	public void setSizeBytes(String sizeBytes) {
		this.sizeBytes = sizeBytes;
	}

	@XmlAttribute(name = "Type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}

class PartitionMemory {
	private int partitionIdentifier;
	private String partitionName;
	private List<MemoryRequirements> memoryRequirements;

	public PartitionMemory() {super();}

	@XmlAttribute(name = "PartitionIdentifier")
	public int getPartitionIdentifier() {
		return partitionIdentifier;
	}

	public void setPartitionIdentifier(int partitionIdentifier) {
		this.partitionIdentifier = partitionIdentifier;
	}

	@XmlAttribute(name = "PartitionName")
	public String getPartitionName() {
		return partitionName;
	}

	public void setPartitionName(String partitionName) {
		this.partitionName = partitionName;
	}

	@XmlElement(name="Memory_Requirements")
	public List<MemoryRequirements> getMemoryRequirements() {
		return memoryRequirements;
	}

	public void setMemoryRequirements(List<MemoryRequirements> memoryRequirements) {
		this.memoryRequirements = memoryRequirements;
	}

}

class PartitionHMTable {
	private int partitionIdentifier;
	private String partitionName;
	private List<SystemStateEntry> systemStateEntries;

	public PartitionHMTable() {super();}

	@XmlAttribute(name = "PartitionIdentifier")
	public int getPartitionIdentifier() {
		return partitionIdentifier;
	}

	public void setPartitionIdentifier(int partitionIdentifier) {
		this.partitionIdentifier = partitionIdentifier;
	}

	@XmlAttribute(name = "PartitionName")
	public String getPartitionName() {
		return partitionName;
	}

	public void setPartitionName(String partitionName) {
		this.partitionName = partitionName;
	}

	@XmlElement(name="System_State_Entry")
	public List<SystemStateEntry> getSystemStateEntries() {
		return systemStateEntries;
	}

	public void setSystemStateEntries(List<SystemStateEntry> systemStateEntries) {
		this.systemStateEntries = systemStateEntries;
	}

}

class StandardPartition {
	private String portName;
	private int partitionIdentifier;
	private String partitionName;

	public StandardPartition() {super();}

	@XmlAttribute(name = "PortName")
	public String getPortName() {
		return portName;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

	@XmlAttribute(name = "PartitionIdentifier")
	public int getPartitionIdentifier() {
		return partitionIdentifier;
	}

	public void setPartitionIdentifier(int partitionIdentifier) {
		this.partitionIdentifier = partitionIdentifier;
	}

	@XmlAttribute(name = "PartitionName")
	public String getPartitionName() {
		return partitionName;
	}

	public void setPartitionName(String partitionName) {
		this.partitionName = partitionName;
	}

}

class Source {
	private StandardPartition standardPartition;

	public Source() {super();}

	@XmlElement(name="Standard_Partition")
	public StandardPartition getStandardPartition() {
		return standardPartition;
	}

	public void setStandardPartition(StandardPartition standardPartition) {
		this.standardPartition = standardPartition;
	}
}

class Destination {
	private StandardPartition standardPartition;

	public Destination() {super();}

	@XmlElement(name="Standard_Partition")
	public StandardPartition getStandardPartition() {
		return standardPartition;
	}

	public void setStandardPartition(StandardPartition standardPartition) {
		this.standardPartition = standardPartition;
	}

}

class Channel {
	private String channelName;
	private int channelIdentifier;

	private Source source;
	private Destination destination;

	public Channel() {super();}

	@XmlAttribute(name = "ChannelName")
	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	@XmlAttribute(name = "ChannelIdentifier")
	public int getChannelIdentifier() {
		return channelIdentifier;
	}

	public void setChannelIdentifier(int channelIdentifier) {
		this.channelIdentifier = channelIdentifier;
	}

	@XmlElement(name="Source")
	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	@XmlElement(name="Destination")
	public Destination getDestination() {
		return destination;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}
}

class ConnectionTable {
	private List<Channel> channels;

	public ConnectionTable() {super();}

	@XmlElement(name="Channel")
	public List<Channel> getChannels() {
		return channels;
	}

	public void setChannels(List<Channel> channels) {
		this.channels = channels;
	}
}

class Window { 
	private double windowDurationSeconds;
	private int windowIdentifier;
	private Partition partition;

	public Window() {super();}

	@XmlAttribute(name = "WindowDurationSeconds")
	public double getWindowDurationSeconds() {
		return windowDurationSeconds;
	}

	public void setWindowDurationSeconds(double windowDurationSeconds) {
		this.windowDurationSeconds = windowDurationSeconds;
	}

	@XmlAttribute(name = "WindowIdentifier")
	public int getWindowIdentifier() {
		return windowIdentifier;
	}

	public void setWindowIdentifier(int windowIdentifier) {
		this.windowIdentifier = windowIdentifier;
	}

	@XmlElement(name="Partition")
	public Partition getPartition() {
		return partition;
	}

	public void setPartition(Partition partition) {
		this.partition = partition;
	}
}

class ModuleSchedule {
	private boolean initialModuleSchedule;
	private double majorFrameSeconds;
	private int scheduleIdentifier;
	private int scheduleName;
	private List<Window> windows;

	public ModuleSchedule() {
		super();
	}

	@XmlAttribute(name = "InitialModuleSchedule")
	public boolean isInitialModuleSchedule() {
		return initialModuleSchedule;
	}

	public void setInitialModuleSchedule(boolean initialModuleSchedule) {
		this.initialModuleSchedule = initialModuleSchedule;
	}

	@XmlAttribute(name = "MajorFrameSeconds")
	public double getMajorFrameSeconds() {
		return majorFrameSeconds;
	}

	public void setMajorFrameSeconds(double majorFrameSeconds) {
		this.majorFrameSeconds = majorFrameSeconds;
	}

	@XmlAttribute(name = "ScheduleIdentifier")
	public int getScheduleIdentifier() {
		return scheduleIdentifier;
	}

	public void setScheduleIdentifier(int scheduleIdentifier) {
		this.scheduleIdentifier = scheduleIdentifier;
	}

	@XmlAttribute(name = "ScheduleName")
	public int getScheduleName() {
		return scheduleName;
	}

	public void setScheduleName(int scheduleName) {
		this.scheduleName = scheduleName;
	}

	@XmlElement(name="Window")
	public List<Window> getWindows() {
		return windows;
	}

	public void setWindows(List<Window> windows) {
		this.windows = windows;
	}

}

class ModExt { 
	private ModuleSchedule moduleSchedule;

	public ModExt() {super();}

	@XmlElement(name="ModuleSchedule")
	public ModuleSchedule getModuleSchedule() {
		return moduleSchedule;
	}

	public void setModuleSchedule(ModuleSchedule moduleSchedule) {
		this.moduleSchedule = moduleSchedule;
	}

}

@XmlRootElement(name = "ARINC_653_Module")
public class ARINC_653_Module implements Serializable {
	@XmlElement(name = "System_HM_Table")
	private SystemHmTable systemHmTable;
	@XmlElement(name = "Module_HM_Table")
	private ModuleHMTable moduleHmTable;
	@XmlElement(name = "Partition")
	private List<Partition> partitions;
	@XmlElement(name = "Partition_Memory")
	private List<PartitionMemory> partitionMemories;
	@XmlElement(name = "Partition_HM_Table")
	private List<PartitionHMTable> partitionHmTables;
	@XmlElement(name = "Connection_Table")
	private ConnectionTable connectionTable;
	@XmlElement(name = "ModExt")
	private ModExt modExt;

	public ARINC_653_Module() {
		super();
	}
}

