package ch.hftm.blog.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "comment")
public class Comment {
    // Getter and Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Size(min = 3, message = "Text needs at least 3 characters")
    private String text;

    @Setter
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Setter
    @Column(name = "email_sent")
    private Boolean emailSent;  // Ob die E-Mail erfolgreich gesendet wurde

    @Setter
    @Column(name = "email_sent_at")
    private LocalDateTime emailSentAt;  // Zeitpunkt des E-Mail-Versands

    @Setter
    @Column(name = "email_message_id", unique = true)
    private String emailMessageId;  // ID der gesendeten E-Mail für Nachverfolgung

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
