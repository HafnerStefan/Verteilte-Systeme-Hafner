package ch.hftm.blog.dto;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDateTime;

@Setter
@Getter
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


    public CommentBaseDTO() {
    }

    public CommentBaseDTO(String text, Long blogId, Long userId) {
        this.text = text;
        this.blogId = blogId;
        this.userId = userId;
    }


    public CommentBaseDTO(Long id, String text, LocalDateTime createdAt, Long blogId, Long userId) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.blogId = blogId;
        this.userId = userId;
    }

}
