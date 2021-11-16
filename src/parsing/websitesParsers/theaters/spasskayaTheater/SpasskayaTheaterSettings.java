package parsing.websitesParsers.theaters.spasskayaTheater;

import parsing.ParserSettings;

public class SpasskayaTheaterSettings extends ParserSettings {
    public SpasskayaTheaterSettings(int start, int end) {
        super(start, end);
        BASE_URL = "https://ekvus-kirov.ru/afisha";
        PREFIX = "show/{CurrentId}";
    }
}
