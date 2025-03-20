package ch.hftm.blog.dto;

import java.time.LocalDateTime;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "BlogResponse", description = "Blog entity")
public class BlogBaseDTO {
    @Schema(required = true, example = "32126319")
    private Long id;
    @Schema(required = true, example = "New Blog")
    private String title;
    @Schema(required = true, example = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus laoreet eu purus ac congue. Proin aliquam in enim aliquet viverra.")
    private String text;
    @Schema(type = SchemaType.STRING, format = "date-time", example = "2023-01-02T12:00:00")
    private LocalDateTime createdAt;
    @Schema(type = SchemaType.STRING, format = "date-time", example = "2023-01-02T12:00:00")
    private LocalDateTime updatedAt;
    @Schema(example = "1245")
    private Long userId;
    @Schema(description = "Username of the blog author")
    private String username;

    // Default constructor
    public BlogBaseDTO() {
    }

    // Constructor without comments
    public BlogBaseDTO(Long id, String title, String text, LocalDateTime createdAt, LocalDateTime updatedAt,
            Long userId,String username) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userId = userId;
        this.username = username;
    }

    // Getter and Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
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
