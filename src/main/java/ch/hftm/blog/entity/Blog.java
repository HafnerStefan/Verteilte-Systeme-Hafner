package ch.hftm.blog.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
@Schema(name = "Blog", description = "Blog entity")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(required = true, example = "32126319")
    private Long id;

    @Schema(required = true, example = "New Blog")
    private String title;

    @Schema(required = true, example = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus laoreet eu purus ac congue. Proin aliquam in enim aliquet viverra.")
    private String text;

    @Schema(required = true, example = "2023-06-15T10:15:30")
    private LocalDateTime createdAt;

    @Schema(example = "2023-06-15T10:15:30")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "List of comments")
    private List<Comment> comments;

    public Blog() {
    }

    public Blog(String title, String text, User user) {
        this.title = title;
        this.text = text;
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getter and Setter
    public Long getId() {
        return id;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Blog blog = (Blog) o;
        return Objects.equals(id, blog.id) &&
                Objects.equals(title, blog.title) &&
                Objects.equals(text, blog.text) &&
                Objects.equals(createdAt, blog.createdAt) &&
                Objects.equals(updatedAt, blog.updatedAt) &&
                Objects.equals(user, blog.user) &&
                Objects.equals(comments, blog.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, text, createdAt, updatedAt, user, comments);
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", user=" + user +
                ", comments=" + comments +
                '}';
    }
}
