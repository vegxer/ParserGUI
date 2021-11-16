package gui.panels.controlPanels;

import gui.model.NestingLevelGUI;

import javax.swing.border.EmptyBorder;

public class ShowControlPanel extends ControlPanel {

    public ShowControlPanel() {
        super();
        setBorder(new EmptyBorder(10, 5, 750, 5));
        addNestingLvl(new NestingLevelGUI("Первый спектакль", "Последний спектакль"));
    }
}
