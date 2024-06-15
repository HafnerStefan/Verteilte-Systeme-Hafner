package ch.hftm.blog.entity;

import java.util.Objects;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
@Schema(name = "Blog", description = "Blog returne")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(required = true, example = "32126319")
    private Long id;
    @Schema(required = true, example = "New Blog")
    String title;
    @Schema(required = true, example = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus laoreet eu purus ac congue. Proin aliquam in enim aliquet viverra.")
    String text;

    @ManyToOne

    @JsonBackReference
    private User user;

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

    //Getter and Setter

    /**
    * @return the id
    */
    public Long getId() {
        return id;
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

    /**
    * @return the user
    */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Blog blog = (Blog) o;
        return id == blog.id &&
                Objects.equals(title, blog.title) &&
                Objects.equals(text, blog.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, text);
    }

    @Override
    public String toString() {

        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text;

    }

}
