package parsing.websitesParsers.theaters.dramaTheater;

import gui.ParserGUI;
import gui.model.ShowGUI;
import gui.panels.resultsPanels.ResultsPanel;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import parsing.NestingLevel;
import parsing.Parser;
import parsing.ParserWorker;
import parsing.handlers.ParserHandler;
import parsing.model.Show;
import time.Date;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class DramaTheaterParserWorker extends ParserWorker<ParserGUI, Show> {
    public DramaTheaterParserWorker(Parser<Show> parser, DramaTheaterSettings parserSettings, ParserGUI sender) {
        super(parser, parserSettings, sender);
    }

    @Override
    protected NestingLevel getFirstLvl() throws IOException {
        return new ShowLevel(parserSettings.getStartPoint(), parserSettings.getEndPoint(),
                loader.getSource().getElementsByClass("t_afisha"));
    }

    private class ShowLevel extends NestingLevel {
        public ShowLevel(int startPoint, int endPoint, Elements elements) {
            super(startPoint, endPoint, elements);
            isFinal = true;
        }

        @Override
        public NestingLevel getNextLvl(Element currElement) {
            return null;
        }

        @Override
        public Element getElementById(int id) {
            if (id > getElements().size())
                return null;
            return getElement(id - 1);
        }
    }


    public static class Completed implements ParserHandler<ParserGUI, String> {
        public void onAction(ParserGUI sender, String message) {

            JOptionPane.showMessageDialog(sender, "Загрузка закончена");
        }
    }

    public static class NewData implements ParserHandler<ParserGUI, Show> {
        public void onAction(ParserGUI sender, Show a) throws IOException {
            JLabel image = new JLabel();
            image.setAlignmentX(Component.LEFT_ALIGNMENT);
            String savedPath = "";
            if (!a.getImage().getUrl().isEmpty()) {
                String path = "Images/DramaTheater/" + a.getName().replaceAll("[?<>|\"*:\\\\/\\n]", "!");
                savedPath = a.getImage().download(path);
            }

            JTextArea headArea = new JTextArea(a.getName());
            headArea.setFont(new Font(Font.SERIF, Font.BOLD, 20));
            JTextArea ageArea = new JTextArea("Возрастное ограничение: " + a.getAgeRestriction());
            ageArea.setFont(new Font(Font.DIALOG, Font.PLAIN, 16));
            JTextArea durationArea = new JTextArea("Продолжительность: " + a.getDuration());
            durationArea.setFont(new Font(Font.DIALOG, Font.PLAIN, 16));
            JTextArea datesArea = new JTextArea();
            String dates = "";
            for (Date date : a.getDates())
                dates = dates.concat(date.toString() + "\n");
            datesArea.setText("Даты:\n" + dates);
            datesArea.setFont(new Font(Font.DIALOG, Font.PLAIN, 16));
            ResultsPanel panel = sender.getResultsPanel();
            panel.addItem(new ShowGUI(headArea, ageArea, durationArea, datesArea, savedPath));
            panel.revalidate();
        }
    }
}
