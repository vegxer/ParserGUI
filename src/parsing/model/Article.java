package parsing.model;


import urlImage.Image;

public class Article {
    private String name, text;
    private final Image image;

    public Article(String name, String text, String imageUrl) {
        setName(name);
        setText(text);
        this.image = new Image(imageUrl);
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        if (text == null)
            throw new NullPointerException();
        this.text = text;
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
}
