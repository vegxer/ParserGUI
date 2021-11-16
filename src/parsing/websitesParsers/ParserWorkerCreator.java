package parsing.websitesParsers;

import gui.ParserGUI;
import gui.model.NestingLevelGUI;
import parsing.ParserWorker;
import parsing.websitesParsers.habr.HabrParser;
import parsing.websitesParsers.habr.HabrParserWorker;
import parsing.websitesParsers.habr.HabrSettings;
import parsing.websitesParsers.theaters.dramaTheater.DramaTheaterParser;
import parsing.websitesParsers.theaters.dramaTheater.DramaTheaterParserWorker;
import parsing.websitesParsers.theaters.dramaTheater.DramaTheaterSettings;
import parsing.websitesParsers.theaters.spasskayaTheater.SpasskayaTheaterParser;
import parsing.websitesParsers.theaters.spasskayaTheater.SpasskayaTheaterParserWorker;
import parsing.websitesParsers.theaters.spasskayaTheater.SpasskayaTheaterSettings;

public final class ParserWorkerCreator {
    private ParserWorkerCreator() {}

    public static ParserWorker create(ParserGUI.Website website, ParserGUI parentFrame) {
        ParserWorker parserWorker;
        NestingLevelGUI firstLvl = parentFrame.getControlPanel().getNestingLevel(0);
        int startPage = Integer.parseInt(firstLvl.getStartPointField().getText());
        int endPage = Integer.parseInt(firstLvl.getEndPointField().getText());

        switch (website) {
            case HABR -> {
                NestingLevelGUI secondLvl = parentFrame.getControlPanel().getNestingLevel(1);
                int startArticle = Integer.parseInt(secondLvl.getStartPointField().getText());
                int endArticle = Integer.parseInt(secondLvl.getEndPointField().getText());
                parserWorker = new HabrParserWorker(new HabrParser(),
                        new HabrSettings(startPage, endPage, startArticle, endArticle), parentFrame);
                parserWorker.onNewData.addOnActionHandler(new HabrParserWorker.NewData());
                parserWorker.onCompleted.addOnActionHandler(new HabrParserWorker.Completed());
            }
            case DRAMA_THEATER -> {
                parserWorker = new DramaTheaterParserWorker(new DramaTheaterParser(),
                        new DramaTheaterSettings(startPage, endPage), parentFrame);
                parserWorker.onNewData.addOnActionHandler(new DramaTheaterParserWorker.NewData());
                parserWorker.onCompleted.addOnActionHandler(new DramaTheaterParserWorker.Completed());
            }
            default -> {
                parserWorker = new SpasskayaTheaterParserWorker(new SpasskayaTheaterParser(),
                        new SpasskayaTheaterSettings(startPage, endPage), parentFrame);
                parserWorker.onNewData.addOnActionHandler(new SpasskayaTheaterParserWorker.NewData());
                parserWorker.onCompleted.addOnActionHandler(new SpasskayaTheaterParserWorker.Completed());
            }
        }

        return parserWorker;
    }
}
