package gui;

import gui.menu.ParserMenu;
import gui.panels.controlPanels.ArticleControlPanel;
import gui.panels.controlPanels.ControlPanel;
import gui.panels.controlPanels.ShowControlPanel;
import gui.panels.resultsPanels.ArticleResultsPanel;
import gui.panels.resultsPanels.ResultsPanel;
import gui.panels.resultsPanels.ShowResultsPanel;
import parsing.ParserWorker;
import parsing.websitesParsers.ParserWorkerCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class ParserGUI extends JFrame {
    public enum Website { HABR, DRAMA_THEATER, SPASSKAYA_THEATER }
    private Website currWebsite;
    private ParserWorker parserWorker;
    private ResultsPanel resultsPanel;
    private ControlPanel controlPanel;


    public ParserGUI() throws HeadlessException {
        System.setProperty("HeadColor", "#e3efee");
        JFrame.setDefaultLookAndFeelDecorated(true);
        setTitle("Парсер Хабра");
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        currWebsite = Website.HABR;

        controlPanel = new ArticleControlPanel();
        controlPanel.addOnStartBtnListener(new StartButtonHandler());
        controlPanel.addOnStopBtnListener(new StopButtonHandler());
        add(controlPanel, BorderLayout.EAST);

        resultsPanel = new ArticleResultsPanel();
        add(resultsPanel, BorderLayout.CENTER);
        resultsPanel.addScrollPane(this);

        ParserMenu menu = new ParserMenu();
        add(menu, BorderLayout.NORTH);
        menu.addWebsite("Хабр");
        menu.addWebsite("Драматический театр");
        menu.addWebsite("Театр на спасской");

        menu.getMenu(0).getItem(0).addActionListener(actionEvent -> {
            enterArticleMode();
            setTitle("Парсер Хабра");
            currWebsite = Website.HABR;
        });
        menu.getMenu(0).getItem(1).addActionListener(actionEvent -> {
            enterShowMode();
            setTitle("Парсер драматического театра");
            currWebsite = Website.DRAMA_THEATER;
        });
        menu.getMenu(0).getItem(2).addActionListener(actionEvent -> {
            enterShowMode();
            setTitle("Парсер Театра на Спасской");
            currWebsite = Website.SPASSKAYA_THEATER;
        });


        setVisible(true);
    }

    private void enterArticleMode() {
        clearFrame();
        controlPanel = new ArticleControlPanel();
        resultsPanel = new ArticleResultsPanel();
        resetFrame();
    }

    private void enterShowMode() {
        clearFrame();
        controlPanel = new ShowControlPanel();
        resultsPanel = new ShowResultsPanel();
        resetFrame();
    }

    private void clearFrame() {
        controlPanel.removeAll();
        resultsPanel.removeAll();
        remove(resultsPanel.getScrollPane());
        remove(controlPanel);
        remove(resultsPanel);
    }

    private void resetFrame() {
        add(controlPanel, BorderLayout.EAST);
        add(resultsPanel, BorderLayout.CENTER);
        resultsPanel.addScrollPane(this);
        controlPanel.addOnStartBtnListener(new StartButtonHandler());
        controlPanel.addOnStopBtnListener(new StopButtonHandler());
        revalidate();
    }


    public class StartButtonHandler extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            try {
                parserWorker = ParserWorkerCreator.create(currWebsite, ParserGUI.this);
            }
            catch (Exception exc) {
                JOptionPane.showMessageDialog(ParserGUI.this,
                        "Неверный ввод параметров парсинга", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }

            resultsPanel.reset();
            Thread parsing = new Thread(() -> {
                try {
                    parserWorker.start();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(ParserGUI.this,
                            ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
                parserWorker.abort();
            });
            parsing.start();
        }
    }

    public class StopButtonHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            parserWorker.abort();
        }
    }

    public ResultsPanel getResultsPanel() {
        return resultsPanel;
    }

    public ControlPanel getControlPanel() { return controlPanel; }
}
