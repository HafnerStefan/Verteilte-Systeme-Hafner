package ch.hftm.blog.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Property;
import dev.morphia.annotations.Reference;
import org.bson.types.ObjectId;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity("comment")
//@Table(name = "comment")
public class Comment {
    // Getter and Setter
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId id;

    @Setter
    @Size(min = 3, message = "Text needs at least 3 characters")
    private String text;

    @Setter
    //@Column(name = "created_at")
    private LocalDateTime createdAt;

    @Setter
    //@ManyToOne
    @Reference
    @JsonIgnore
    @NotNull(message = "Comment need a Blog")
    private Blog blog;

    @Setter
    //@ManyToOne
    @Reference
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
