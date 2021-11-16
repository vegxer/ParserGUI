package parsing.websitesParsers.theaters.dramaTheater;

import parsing.ParserSettings;

public class DramaTheaterSettings extends ParserSettings {
    public DramaTheaterSettings(int start, int end) {
        super(start, end);
        BASE_URL = "https://kirovdramteatr.ru";
        PREFIX = "afisha";
    }
}
