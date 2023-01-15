package part_generator.part_param_container;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

class Error_ID_Level { 
	public String ErrorIdentifier;
	public String ErrorLevel;
	public String ErrorCode;
}

class System_State_Entry { 
	public List<Error_ID_Level> Error_ID_Level;
	public String SystemState;
	public List<Error_ID_Action> Error_ID_Action;
}

class System_HM_Table { 
	public List<System_State_Entry> System_State_Entry;
}

class Error_ID_Action { 
	public String ErrorIdentifier;
	public String Action;
}

class Module_HM_Table { 
	public List<System_State_Entry> System_State_Entry;
}

class Queuing_Port { 
	public String Name;
	public int MaxNbMessages;
	public int MaxMessageSize;
	public String Direction;
	public String Redir;
}

class Partition { 
	public List<Queuing_Port> Queuing_Port;
	public String PartitionName;
	public String EntryPoint;
	public int PartitionIdentifier;
	public boolean SystemPartition;
	public List<Sampling_Port> Sampling_Port;
	public String Criticality;
	public boolean PartitionPeriodStart;
	public int PartitionMinPrio;
}

class Sampling_Port { 
	public double RefreshRateSeconds;
	public String Direction;
	public int MaxMessageSize;
	public String Name;
}

class Memory_Requirements { 
	public String SizeBytes;
	public String Type;
}

class Partition_Memory { 
	public List<Memory_Requirements> Memory_Requirements;
	public int PartitionIdentifier;
	public String PartitionName;
}

class Partition_HM_Table { 
	public List<System_State_Entry> System_State_Entry;
	public int PartitionIdentifier;
	public String PartitionName;
}

class Standard_Partition { 
	public String PortName;
	public int PartitionIdentifier;
	public String PartitionName;
}

class Source { 
	public Standard_Partition Standard_Partition;
}

class Destination { 
	public Standard_Partition Standard_Partition;
}

class Channel { 
	public Source Source;
	public Destination Destination;
	public String ChannelName;
	public int ChannelIdentifier;
}

class Connection_Table { 
	public List<Channel> Channel;
}

class Window { 
	public Partition Partition;
	public double WindowDurationSeconds;
	public int WindowIdentifier;
}

class ModuleSchedule { 
	public List<Window> Window;
	public boolean InitialModuleSchedule;
	public double MajorFrameSeconds;
	public int ScheduleIdentifier;
	public int ScheduleName;
}

class ModExt { 
	public ModuleSchedule ModuleSchedule;
}

@XmlRootElement
public class ARINC_653_Module { 
	@XmlElement
	public System_HM_Table System_HM_Table;
	@XmlElement
	public Module_HM_Table Module_HM_Table;
	@XmlElement
	public List<Partition> Partition;
	@XmlElement
	public List<Partition_Memory> Partition_Memory;
	@XmlElement
	public List<Partition_HM_Table> Partition_HM_Table;
	@XmlElement
	public Connection_Table Connection_Table;
	@XmlElement
	public ModExt ModExt;
	@XmlElement
	public String xsi;
	@XmlElement
	public String noNamespaceSchemaLocation;
	@XmlElement
	public String ModuleName;
	@XmlElement
	public double ModuleVersion;
	@XmlElement
	public int ModuleId;
}

