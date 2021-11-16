package gui.panels.controlPanels;

import gui.model.NestingLevelGUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

public class ControlPanel extends JPanel {
    private final ArrayList<NestingLevelGUI> nestingLevels;
    private final JButton startBtn;
    private final JButton stopBtn;


    public ControlPanel() {
        setLayout(new GridLayout(0, 2, 5, 12));
        Font btnFont = new Font(Font.DIALOG, Font.BOLD, 30);
        startBtn = new JButton("Старт");
        startBtn.setFont(btnFont);
        stopBtn = new JButton("Стоп");
        stopBtn.setFont(btnFont);
        nestingLevels = new ArrayList<>();
        add(startBtn);
        add(stopBtn);
    }

    public void addNestingLvl(NestingLevelGUI nestingLvl) {
        if (nestingLvl == null)
            throw new NullPointerException();
        nestingLevels.add(nestingLvl);
        nestingLvl.getStartPointLabel().setBorder(new EmptyBorder(40, 5, 0, 5));
        nestingLvl.getStartPointLabel().setFont(new Font(Font.DIALOG, Font.PLAIN, 22));
        nestingLvl.getEndPointLabel().setBorder(new EmptyBorder(40, 5, 0, 5));
        nestingLvl.getEndPointLabel().setFont(new Font(Font.DIALOG, Font.PLAIN, 22));
        nestingLvl.getStartPointField().setFont(new Font(Font.DIALOG, Font.BOLD, 26));
        nestingLvl.getEndPointField().setFont(new Font(Font.DIALOG, Font.BOLD, 26));
        add(nestingLvl.getStartPointLabel());
        add(nestingLvl.getEndPointLabel());
        add(nestingLvl.getStartPointField());
        add(nestingLvl.getEndPointField());
    }

    public NestingLevelGUI getNestingLevel(int index) {
        return nestingLevels.get(index);
    }

    public void addOnStartBtnListener(MouseAdapter mouseAdapter) {
        addBtnListener(startBtn, mouseAdapter);
    }

    public void addOnStopBtnListener(MouseAdapter mouseAdapter) {
        addBtnListener(stopBtn, mouseAdapter);
    }

    private void addBtnListener(JButton button, MouseAdapter listener) {
        if (listener == null)
            throw new NullPointerException();
        button.addMouseListener(listener);
    }
}
