package ch.hftm.blog.dto.requerstDTO;

import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.User;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDateTime;


@Schema(name = "CommentRequest", description = "Request payload for creating comment")
public class CommentRequest {

    @Schema(required = true, example = "Great post!")
    private String text;

    @Schema(type = SchemaType.STRING, format = "date-time", example = "2023-01-02T12:00:00")
    private LocalDateTime createdAt;

    @Schema(example = "xxx")
    private User user;

    @Schema(example = "xxxx")
    private Blog blog;


    public CommentRequest() {
    }

    public CommentRequest(String text, User user, Blog blog) {
        this.text = text;
        this.user = user;
        this.blog = blog;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
