package ch.hftm.blog.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
@Schema(name = "Comment", description = "Comment entity")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(required = true, example = "987654")
    private Long id;

    @Schema(required = true, example = "Great post!")
    private String text;

    @Schema(required = true, example = "2023-06-15T10:15:30")
    private LocalDateTime createdAt;

    @ManyToOne
    @JsonIgnore
    private Blog blog;

    @ManyToOne
    @JsonIgnore
    private User user;

    public Comment() {
    }

    public Comment(String text, User user, Blog blog) {
        this.text = text;
        this.user = user;
        this.blog = blog;
        this.createdAt = LocalDateTime.now();
    }

    // Getter and Setter
    public Long getId() {
        return id;
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

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) &&
                Objects.equals(text, comment.text) &&
                Objects.equals(createdAt, comment.createdAt) &&
                Objects.equals(blog, comment.blog) &&
                Objects.equals(user, comment.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, createdAt, blog, user);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", createdAt=" + createdAt +
                ", blog=" + blog +
                ", user=" + user +
                '}';
    }
}
