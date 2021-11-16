package parsing.websitesParsers.habr;

import parsing.ParserSettings;

public class HabrSettings extends ParserSettings {
    private int startArticle, endArticle;

    public HabrSettings(int start, int end, int startArticle, int endArticle) {
        super(start, end);
        BASE_URL = "https://habr.com/ru/all";
        PREFIX = "page{CurrentId}";
        setStartArticle(startArticle);
        setEndArticle(endArticle);
    }


    public int getStartArticle() {
        return startArticle;
    }

    public void setStartArticle(int startArticle) {
        if (startArticle < 1)
            throw new IllegalArgumentException("Неверно указан номер страницы");
        this.startArticle = startArticle;
    }

    public int getEndArticle() {
        return endArticle;
    }

    public void setEndArticle(int endArticle) {
        if (endArticle < startArticle)
            throw new IllegalArgumentException("Неверно указан номер страницы");
        this.endArticle = endArticle;
    }
}
