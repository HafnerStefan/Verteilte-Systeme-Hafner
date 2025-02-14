package ch.hftm.blog.dto;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(name = "CommentResponse", description = "Blog entity")
public class CommentBaseDTO {
    @Schema(required = true, example = "32126319")
    private ObjectId id;
    @Schema(required = true, example = "Lorem ipsum dolor sit amet, consectetur adipiscing")
    private String text;
    @Schema(type = SchemaType.STRING, format = "date-time", example = "2023-01-02T12:00:00")
    private LocalDateTime createdAt;
    @Schema(example = "1245")
    private ObjectId blogId;
    @Schema(example = "1245")
    private ObjectId userId;
    @Schema(description = "Username of the comment author")
    private String username;

    public CommentBaseDTO() {
    }

    public CommentBaseDTO(String text, ObjectId blogId, ObjectId userId) {
        this.text = text;
        this.blogId = blogId;
        this.userId = userId;
    }

    public CommentBaseDTO(ObjectId id, String text, LocalDateTime createdAt, ObjectId blogId, ObjectId userId) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.blogId = blogId;
        this.userId = userId;
    }

}
