package ch.hftm.blog.boundry;

public class BlogRequest {
    private String title;
    private String text;

    // Standardkonstruktor
    public BlogRequest() {
    }

    // Konstruktor mit Parametern
    public BlogRequest(String title, String text) {
        this.title = title;
        this.text = text;
    }

    // Getter und Setter
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
