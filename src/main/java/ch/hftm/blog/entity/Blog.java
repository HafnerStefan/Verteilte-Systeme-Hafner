package ch.hftm.blog.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Blog {
    @Id
    @GeneratedValue

    private long id;

    String title;
    String text;

    public Blog() {
    }

    /**
     * @param title
     * @param text
     */
    public Blog(String title, String text) {
        this.title = title;
        this.text = text;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

}
