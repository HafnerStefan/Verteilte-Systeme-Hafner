package ch.hftm.blog.dto;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Schema(name = "BlogResponse", description = "Blog entity")
public class BlogBaseDTO {
    @Schema(required = true, example = "32126319")
    private Long id;
    @Schema(required = true, example = "New Blog")
    private String title;
    @Schema(required = true, example = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus laoreet eu purus ac congue. Proin aliquam in enim aliquet viverra.")
    private String text;
    @Schema(required = true, example = "2023-06-15T10:15:30")
    private LocalDateTime createdAt;
    @Schema(example = "2023-06-15T10:15:30")
    private LocalDateTime updatedAt;
    @Schema(example = "1245")
    private Long userId;

    // Default constructor
    public BlogBaseDTO() {
    }

    // Constructor ADD NEW BLOG
    public BlogBaseDTO(String title, String text, Long userId) {
        this.title = title;
        this.text = text;
        this.userId = userId;
    }

    // Constructor without comments
    public BlogBaseDTO(Long id, String title, String text, LocalDateTime createdAt, LocalDateTime updatedAt, Long userId) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userId = userId;
    }



}
