package ch.hftm.blog.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Entity("blogs")
//@Table(name = "blog")

public class Blog {
    // Getter and Setter
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)

    private ObjectId id;

    @Setter
    @Size(min = 3, message = "Title needs at least 3 characters")
    private String title;

    @Setter
    @Size(min = 10, message = "Text needs at least 10 characters")
    private String text;

    @Setter
    //@Column(name = "created_at")
    private LocalDateTime createdAt;

    @Setter
    //@Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Setter
    //@ManyToOne
    @Reference
    @NotNull(message = "A Blog need a user")
    @JsonBackReference
    private User user;

    @Setter
    //@OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
    @Reference
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
