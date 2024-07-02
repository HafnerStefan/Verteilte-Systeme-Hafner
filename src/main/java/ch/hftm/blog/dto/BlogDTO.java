package ch.hftm.blog.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class BlogDTO {

    private Long id;
    private String title;
    private String text;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;
    private List<Long> commentsIds;

    // Default constructor
    public BlogDTO() {
    }

    // Constructor ADD NEW BLOG
    public BlogDTO(String title, String text, Long userId) {
        this.title = title;
        this.text = text;
        this.userId = userId;
    }

    // Constructor without comments
    public BlogDTO(Long id, String title, String text, LocalDateTime createdAt, LocalDateTime updatedAt, Long userId) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userId = userId;
    }

    // Constructor with all parameters
    public BlogDTO(Long id, String title, String text, LocalDateTime createdAt, LocalDateTime updatedAt, Long userId,
            List<Long> commentsIds) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userId = userId;
        this.commentsIds = commentsIds;
    }

}
