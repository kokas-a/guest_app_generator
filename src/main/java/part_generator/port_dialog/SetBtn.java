package part_generator.port_dialog;

import lombok.Getter;

import javax.swing.*;

public class SetBtn extends JButton {

    @Getter
    private final int portId;
    private static final long serialVersionUID = 1L;

    public SetBtn(int relPortId) {
        portId = relPortId;
        new JButton();
    }

}
