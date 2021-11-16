package gui.menu;

import javax.swing.*;

public class ParserMenu extends JMenuBar {
    private final JMenu websitesMenu;

    public ParserMenu() {
        websitesMenu = new JMenu("Сайт");
        add(websitesMenu);
    }

    public void addWebsite(String websiteName) {
        websitesMenu.add(new JMenuItem(websiteName));
    }
}
