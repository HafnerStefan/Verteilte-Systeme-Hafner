package ch.hftm.blog.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter
@Entity
@Schema(name = "Comment", description = "Comment entity")
public class Comment {
    // Getter and Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(required = true, example = "987654")
    private Long id;

    @Setter
    @Schema(required = true, example = "Great post!")
    @Size(min = 20, message = "Text needs at least 20 characters")
    private String text;

    @Setter
    @Schema(required = true, example = "2023-06-15T10:15:30")
    private LocalDateTime createdAt;

    @Setter
    @ManyToOne
    @JsonIgnore
    @NotNull(message = "Comment need a Blog")
    private Blog blog;

    @Setter
    @ManyToOne
    @JsonIgnore
    @NotNull(message = "Comment need a User")
    private User user;

    public Comment() {
    }

    public Comment(String text, User user, Blog blog) {
        this.text = text;
        this.user = user;
        this.blog = blog;
        this.createdAt = LocalDateTime.now();
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
