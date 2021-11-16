package parsing;

import org.jsoup.nodes.Element;
import parsing.handlers.HandlerController;

import java.io.IOException;

public abstract class ParserWorker<S, T> {
    protected final Parser<T> parser;
    protected final ParserSettings parserSettings;
    protected final HtmlLoader loader;
    private boolean isActive;
    public final HandlerController<S, T> onNewData;
    public final HandlerController<S, String> onCompleted;


    public ParserWorker(Parser<T> parser, ParserSettings parserSettings, S sender) {
        onNewData = new HandlerController<>(sender);
        onCompleted = new HandlerController<>(sender);
        this.parser = parser;
        loader = new HtmlLoader(ParserSettings.BASE_URL + "/" + ParserSettings.PREFIX);
        this.parserSettings = parserSettings;
    }


    public void start() throws IOException {
        isActive = true;
        work();
    }

    public void abort() {
        isActive = false;
    }

    protected abstract NestingLevel getFirstLvl() throws IOException;


    private void work() throws IOException {
        work(getFirstLvl());
        onCompleted.onAction("Загрузка закончена");
        isActive = false;
    }

    private void work(NestingLevel nestLvl) throws IOException {
        if (nestLvl.isFinal())
            parseLastLvl(nestLvl);
        else {
            for (int i = nestLvl.getStartPoint(); i <= nestLvl.getEndPoint(); ++i) {
                Element currElement = nestLvl.getElementById(i);
                if (currElement == null || !isActive)
                    return;
                work(nestLvl.getNextLvl(currElement));
            }
        }
    }

    private void parseLastLvl(NestingLevel lastLvl) throws IOException {
        for (int i = lastLvl.getStartPoint(); i <= lastLvl.getEndPoint(); ++i) {
            Element currElement = lastLvl.getElementById(i);
            if (currElement == null || !isActive)
                return;
            T result = parser.parse(currElement);
            onNewData.onAction(result);
        }
    }

}
