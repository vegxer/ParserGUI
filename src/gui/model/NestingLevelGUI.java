package gui.model;

import javax.swing.*;

public class NestingLevelGUI {
    private final JTextField startPointField;
    private final JTextField endPointField;
    private final JLabel startPointLabel;
    private final JLabel endPointLabel;

    public NestingLevelGUI(String startPointText, String endPointText) {
        startPointField = new JTextField();
        endPointField = new JTextField();
        startPointLabel = new JLabel(startPointText);
        endPointLabel = new JLabel(endPointText);
    }


    public JTextField getStartPointField() {
        return startPointField;
    }

    public JTextField getEndPointField() {
        return endPointField;
    }

    public JLabel getStartPointLabel() {
        return startPointLabel;
    }

    public JLabel getEndPointLabel() {
        return endPointLabel;
    }
}
