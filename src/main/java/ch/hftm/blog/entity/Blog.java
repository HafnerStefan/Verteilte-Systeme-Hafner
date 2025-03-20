package ch.hftm.blog.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.graphql.Type;


@Entity
@Table(name = "blog")
@Type("Blog")
public class Blog {
    // Getter and Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Size(min = 3, message = "Title needs at least 3 characters")
    private String title;

    @Size(min = 10, message = "Text needs at least 10 characters")
    private String text;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @NotNull(message = "A Blog need a user")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @Size(min = 3, message = "Title needs at least 3 characters") String getTitle() {
        return title;
    }

    public void setTitle(@Size(min = 3, message = "Title needs at least 3 characters") String title) {
        this.title = title;
    }

    public @Size(min = 10, message = "Text needs at least 10 characters") String getText() {
        return text;
    }

    public void setText(@Size(min = 10, message = "Text needs at least 10 characters") String text) {
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

    public @NotNull(message = "A Blog need a user") User getUser() {
        return user;
    }

    public void setUser(@NotNull(message = "A Blog need a user") User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
