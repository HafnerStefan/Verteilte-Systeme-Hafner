package ch.hftm.blog.dto;

import java.time.LocalDateTime;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "CommentResponse", description = "Blog entity")
public class CommentBaseDTO {
    @Schema(required = true, example = "32126319")
    private Long id;

    @Schema(required = true, example = "Lorem ipsum dolor sit amet, consectetur adipiscing")
    private String text;

    @Schema(type = SchemaType.STRING, format = "date-time", example = "2023-01-02T12:00:00")
    private LocalDateTime createdAt;

    @Schema(example = "1245")
    private Long blogId;

    @Schema(example = "1245")
    private Long userId;

    @Schema(description = "Username of the comment author")
    private String username;

    public CommentBaseDTO() {
    }

    public CommentBaseDTO(Long id, String text, LocalDateTime createdAt, Long blogId, Long userId) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.blogId = blogId;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
