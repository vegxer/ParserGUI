package parsing.websitesParsers.habr;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import parsing.Parser;
import parsing.model.Article;

public class HabrParser implements Parser<Article> {
    public Article parse(Element article) {
        try {
            Element content = article.getElementsByClass("tm-article-body tm-article-snippet__lead").get(0);
            Elements image = content.getElementsByTag("img");
            String imageUrl = image.size() != 0 ? image.get(0).attributes().get("src") : "";
            Elements textElement = content.getElementsByClass("article-formatted-body article-formatted-body_version-1");
            if (textElement.size() == 0)
                textElement = content.getElementsByClass("article-formatted-body article-formatted-body_version-2");
            return new Article(article.getElementsByClass("tm-article-snippet__title-link").text(),
                    textElement.text(), imageUrl);
        }
        catch (Exception exc) {
            return new Article("Ошибка загрузки", "", "");
        }
    }

}
