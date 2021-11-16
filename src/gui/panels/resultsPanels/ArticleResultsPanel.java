package gui.panels.resultsPanels;

import gui.model.ArticleGUI;

import java.io.IOException;

public class ArticleResultsPanel extends ResultsPanel<ArticleGUI> {
    public ArticleResultsPanel() {
        super();
    }

    @Override
    public void addItem(ArticleGUI article) throws IOException {
        if (article == null)
            throw new NullPointerException();

        setItemName("Статья №" + ++itemsCount);
        setHead(article.headField());
        setImage(article.imagePath());
        article.textField().setEditable(false);
        add(article.textField());
    }
}
