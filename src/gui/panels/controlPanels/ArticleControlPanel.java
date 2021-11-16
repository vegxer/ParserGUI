package gui.panels.controlPanels;

import gui.model.NestingLevelGUI;

import javax.swing.border.EmptyBorder;

public class ArticleControlPanel extends ControlPanel {

    public ArticleControlPanel() {
        super();
        setBorder(new EmptyBorder(10, 5, 600, 5));
        addNestingLvl(new NestingLevelGUI("Первая страница", "Последняя страница"));
        addNestingLvl(new NestingLevelGUI("Первая статья", "Последняя статья"));
    }
}
