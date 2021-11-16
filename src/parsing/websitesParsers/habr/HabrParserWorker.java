package parsing.websitesParsers.habr;

import gui.ParserGUI;
import gui.model.ArticleGUI;
import gui.panels.resultsPanels.ResultsPanel;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import parsing.NestingLevel;
import parsing.Parser;
import parsing.ParserWorker;
import parsing.handlers.ParserHandler;
import parsing.model.Article;
import textEditor.Text;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class HabrParserWorker extends ParserWorker<ParserGUI, Article> {

    public HabrParserWorker(Parser<Article> parser, HabrSettings parserSettings, ParserGUI sender) {
        super(parser, parserSettings, sender);
    }

    @Override
    protected NestingLevel getFirstLvl() {
        return new PageLevel(parserSettings.getStartPoint(), parserSettings.getEndPoint());
    }

    private class PageLevel extends NestingLevel {
        public PageLevel(int startPoint, int endPoint) {
            super(startPoint, endPoint);
        }

        @Override
        public NestingLevel getNextLvl(Element currElement) {
            HabrSettings settings = (HabrSettings)parserSettings;
            return new ArticleLevel(settings.getStartArticle(), settings.getEndArticle(),
                    currElement.getElementsByTag("article"));
        }

        @Override
        public Element getElementById(int id) throws IOException {
            return loader.getSourceByPageId(Integer.toString(id));
        }
    }

    private class ArticleLevel extends NestingLevel {
        public ArticleLevel(int startPoint, int endPoint, Elements elements) {
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

    public static class NewData implements ParserHandler<ParserGUI, Article> {
        public void onAction(ParserGUI sender, Article a) throws IOException {
            JLabel image = new JLabel();
            image.setAlignmentX(Component.LEFT_ALIGNMENT);
            String savedPath = "";
            if (!a.getImage().getUrl().isEmpty()) {
                String path = "Images/Habr/" + a.getName().replaceAll("[?<>|\"*:\\\\/\\n]", "!");
                savedPath = a.getImage().download(path);
            }

            JTextArea headArea = new JTextArea(a.getName());
            headArea.setFont(new Font(Font.SERIF, Font.BOLD, 20));
            JTextArea textArea = new JTextArea(Text.splitByLines(a.getText() + "\n", 170));
            textArea.setFont(new Font(Font.DIALOG, Font.PLAIN, 16));
            ResultsPanel panel = sender.getResultsPanel();
            panel.addItem(new ArticleGUI(headArea, textArea, savedPath));
            panel.revalidate();
        }
    }
}
