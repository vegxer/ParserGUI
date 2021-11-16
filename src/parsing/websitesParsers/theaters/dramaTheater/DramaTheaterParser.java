package parsing.websitesParsers.theaters.dramaTheater;

import time.Date;
import org.jsoup.nodes.Element;
import parsing.*;
import parsing.model.Show;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class DramaTheaterParser implements Parser<Show> {
    public Show parse(Element elem) {
        ArrayList<String> date = new ArrayList<>(Arrays.asList(elem.getElementsByClass("date_afisha")
                .text().split("(\\sв\\s)|[\\s:]")));
        removeEmptyEntries(date);
        Date showDate = new Date();
        showDate.setDate(Integer.parseInt(date.get(0)));
        showDate.setMonth(date.get(1));
        showDate.setHours(Integer.parseInt(date.get(2)));
        showDate.setMinutes(Integer.parseInt(date.get(3)));
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        if (showDate.getMonth() < calendar.get(Calendar.MONTH))
            ++year;
        showDate.setYear(year);
        String ageRestriction = elem.getElementsByClass("value_limit_m").text();
        String name = elem.getElementsByTag("a").text();
        String duration = elem.getElementsByClass("td2").text();
        duration = duration.substring(duration.contains("Продолжительность: ") ? 19 : 0, duration.lastIndexOf(ageRestriction));
        return new Show(
                name.substring(0, name.lastIndexOf(ageRestriction)),
                new ArrayList<>(List.of(showDate)),
                duration,
                ageRestriction,
                ParserSettings.BASE_URL + elem.getElementsByClass("td2").get(0)
                        .getElementsByTag("img").get(0).attributes().get("src"));
    }

    private void removeEmptyEntries(ArrayList<String> list) {
        list.removeIf(str -> str.isEmpty() || str.isBlank());
    }

    /*public Show parse(Element document) {
        ArrayList<Show> list = new ArrayList<>();

        Elements afisha = document.getElementsByClass("t_afisha");
        int prevMonthNum = Integer.parseInt(afisha.get(0).getElementsByClass("td1").get(0)
                .getElementsByClass("num").text());
        Date currDate = getStartDate(document);

        for (Element elem : afisha) {
            Element info = elem.getElementsByClass("td1").get(0);

            int monthNum = Integer.parseInt(info.getElementsByClass("num").text());
            if (prevMonthNum > monthNum)
                currDate.addMonths(1);
            prevMonthNum = monthNum;

            String ageRestriction = elem.getElementsByClass("value_limit_m").text();
            String name = elem.getElementsByTag("a").text();
            String duration = elem.getElementsByClass("td2").text();
            duration = duration.substring(duration.contains("Продолжительность: ") ? 19 : 0, duration.lastIndexOf(ageRestriction));
            list.add(new Show(
                    name.substring(0, name.lastIndexOf(ageRestriction)),
                    new ArrayList<>(List.of(getCurrDate(currDate, info, monthNum))),
                    duration,
                    ageRestriction,
                    ParserSettings.BASE_URL + elem.getElementsByClass("td2").get(0)
                            .getElementsByTag("img").get(0).attributes().get("src")
            ));
        }
        return new Show();
    }*/

    /*private Date getStartDate(Element document) {
        String[] currMonthAndYear = document.getElementsByClass("h2").get(0).text().split(" ");

        Date date = new Date();
        date.setMonth(currMonthAndYear[0]);
        date.setYear(Integer.parseInt(currMonthAndYear[1]));

        return date;
    }

    private Date getCurrDate(Date calendar, Element info, int monthNum) {
        Date showDate = new Date();
        showDate.setYear(calendar.getYear());
        showDate.setMonth(calendar.getMonth());
        showDate.setDate(monthNum);
        String[] hoursMinutes = info.getElementsByClass("time").text().split(":");
        showDate.setHours(Integer.parseInt(hoursMinutes[0]));
        showDate.setMinutes(Integer.parseInt(hoursMinutes[1]));

        return showDate;
    }*/
}
