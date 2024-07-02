package ch.hftm.blog.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter
@Entity
@Schema(name = "Blog", description = "Blog entity")
public class Blog {
    // Getter and Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(required = true, example = "32126319")
    private Long id;

    @Setter
    @Schema(required = true, example = "New Blog")
    @Size(min = 5, message = "Title needs at least 5 characters")
    private String title;

    @Setter
    @Schema(required = true, example = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus laoreet eu purus ac congue. Proin aliquam in enim aliquet viverra.")
    @Size(min = 20, message = "Title needs at least 20 characters")
    private String text;

    @Setter
    @Schema(required = true, example = "2023-06-15T10:15:30")
    private LocalDateTime createdAt;

    @Setter
    @Schema(example = "2023-06-15T10:15:30")
    private LocalDateTime updatedAt;

    @Setter
    @ManyToOne
    @NotNull(message = "A Blog need a user")
    @JsonBackReference
    private User user;

    @Setter
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
