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



@Entity
@Table(name = "comment")
public class Comment {
    // Getter and Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, message = "Text needs at least 3 characters")
    private String text;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "email_sent")
    private Boolean emailSent;  // Ob die E-Mail erfolgreich gesendet wurde

    @Column(name = "email_sent_at")
    private LocalDateTime emailSentAt;  // Zeitpunkt des E-Mail-Versands

    @Column(name = "email_message_id", unique = true)
    private String emailMessageId;  // ID der gesendeten E-Mail für Nachverfolgung

    @ManyToOne
    @JsonIgnore
    @NotNull(message = "Comment need a Blog")
    private Blog blog;

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

    // Getter and Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @Size(min = 3, message = "Text needs at least 3 characters") String getText() {
        return text;
    }

    public void setText(@Size(min = 3, message = "Text needs at least 3 characters") String text) {
        this.text = text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getEmailSent() {
        return emailSent;
    }

    public void setEmailSent(Boolean emailSent) {
        this.emailSent = emailSent;
    }

    public LocalDateTime getEmailSentAt() {
        return emailSentAt;
    }

    public void setEmailSentAt(LocalDateTime emailSentAt) {
        this.emailSentAt = emailSentAt;
    }

    public String getEmailMessageId() {
        return emailMessageId;
    }

    public void setEmailMessageId(String emailMessageId) {
        this.emailMessageId = emailMessageId;
    }

    public @NotNull(message = "Comment need a Blog") Blog getBlog() {
        return blog;
    }

    public void setBlog(@NotNull(message = "Comment need a Blog") Blog blog) {
        this.blog = blog;
    }

    public @NotNull(message = "Comment need a User") User getUser() {
        return user;
    }

    public void setUser(@NotNull(message = "Comment need a User") User user) {
        this.user = user;
    }
}
