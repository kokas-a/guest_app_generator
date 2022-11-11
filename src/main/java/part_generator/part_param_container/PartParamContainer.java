package part_generator.part_param_container;

import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;


@XmlRootElement
@Data
public class PartParamContainer {

    @XmlElement
    String partName;
    @XmlElement
    boolean systemPartition;
    @XmlElement
    MemParam mem = new MemParam();
    @XmlElement
    List<PortParam> ports = new ArrayList<>();
    //ArrayList<Map<String, String>> ports = new ArrayList<Map<String, String>>();

    String osRootPath;

}

