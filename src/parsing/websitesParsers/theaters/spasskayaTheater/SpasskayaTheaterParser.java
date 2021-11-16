package parsing.websitesParsers.theaters.spasskayaTheater;

import listShell.ArrayListShell;
import time.Date;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import parsing.*;
import parsing.model.Show;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class SpasskayaTheaterParser implements Parser<Show> {

    public Show parse(Element document) {
        Show show = new Show();
        Element main = document.getElementsByClass("page_box").get(0);
        Element table = main.getElementsByTag("table").get(0);
        show.setName(table.getElementsByTag("tr").get(1).text());
        Element imageElement = table.getElementsByTag("tr").get(3)
                .getElementById("photo_osnova");
        if (imageElement == null)
            show.setImageUrl(ParserSettings.BASE_URL.substring(0, ParserSettings.BASE_URL.lastIndexOf('/'))
                    + main.getElementsByTag("img").get(1).attributes()
                    .get("src").replace("../../", "/"));
        else
            show.setImageUrl(ParserSettings.BASE_URL.substring(0, ParserSettings.BASE_URL.lastIndexOf('/'))
                    + imageElement.attributes().get("src"));
        String ageRestriction = getAgeRestriction(main.getElementsByTag("h2"));
        show.setAgeRestriction(Objects.requireNonNullElse(ageRestriction, "не указано"));
        String text = main.getElementsByTag("p").text();
        if (!text.contains("Продолжительность спектакля - "))
            show.setDuration("не указана");
        else
            show.setDuration(text.substring(text.indexOf("Продолжительность спектакля - ") + 30,
                    text.indexOf('.', text.indexOf("Продолжительность спектакля - ") + 30)));
        show.setDates(getDate(main.getElementsByTag("a")));
        return show;
    }

    private String getAgeRestriction(Elements elems) {
        for (Element elem : elems) {
            if (elem.text().contains("Возрастное ограничение: ") || elem.text().contains("Рекомендация по возрасту: ")
                    || elem.text().contains("Возрастная рекомендация: "))
                return elem.text().substring(elem.text().indexOf(':') + 2);
        }

        return null;
    }

    private ArrayList<Date> getDate(Elements schedule) {
        ArrayListShell<Date> dates = new ArrayListShell<>();

        for (int i = 0; schedule.get(i).attributes().get("title").equals("Купить билет"); ++i) {
            Date date = new Date();
            ArrayList<String> splittedDate = new ArrayList<>(Arrays.asList(schedule.get(i).text().split("(\\sв\\s)|[\\s:]")));
            removeEmptyEntries(splittedDate);
            date.setDate(Integer.parseInt(splittedDate.get(0)));
            date.setMonth(splittedDate.get(1));
            date.setYear(Integer.parseInt(splittedDate.get(2)));
            date.setHours(Integer.parseInt(splittedDate.get(3)));
            date.setMinutes(Integer.parseInt(splittedDate.get(4)));
            if (!dates.contains(x -> date.toString().equals(x.toString())))
                dates.add(date);
        }

        return dates;
    }

    private void removeEmptyEntries(ArrayList<String> list) {
        list.removeIf(str -> str.isEmpty() || str.isBlank());
    }
}
