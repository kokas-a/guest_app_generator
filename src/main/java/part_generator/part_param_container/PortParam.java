package part_generator.part_param_container;

import lombok.Data;

@Data
public class PortParam {

    String portName;
    String portType;
    String portDirection;
    String maxMsgNum;
    String maxMsgLen;

}
