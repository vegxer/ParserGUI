package parsing.model;


import time.Date;
import urlImage.Image;

import java.util.ArrayList;

public class Show {
    private String name, ageRestriction;
    private ArrayList<Date> dates;
    private String duration;
    private final Image image;


    public Show() { image = new Image(); }

    public Show(String name, ArrayList<Date> date, String duration, String ageRestriction, String imageUrl) {
        this.name = name;
        this.dates = date;
        this.duration = duration;
        this.ageRestriction = ageRestriction;
        this.image = new Image(imageUrl);
    }


    public ArrayList<Date> getDates() {
        return dates;
    }

    public void setDates(ArrayList<Date> dates) {
        if (dates == null)
            throw new NullPointerException();
        this.dates = dates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null)
            throw new NullPointerException();
        this.name = name;
    }

    public Image getImage() {
        return image;
    }

    public void setImageUrl(String imageUrl) {
        if (imageUrl == null)
            throw new NullPointerException();
        image.setUrl(imageUrl);
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        if (duration == null)
            throw new NullPointerException();
        this.duration = duration;
    }

    public String getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(String ageRestriction) {
        if (ageRestriction == null)
            throw new NullPointerException();
        this.ageRestriction = ageRestriction;
    }
}
