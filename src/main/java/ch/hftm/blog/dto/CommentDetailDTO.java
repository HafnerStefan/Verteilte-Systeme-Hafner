package ch.hftm.blog.dto;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(name = "CommentDetailResponse", description = "Detailed DTO for a comment, including user information")
public class CommentDetailDTO extends CommentBaseDTO {

    @Schema(description = "Username of the comment author", example = "johndoe")
    private String username;

    public CommentDetailDTO() {
        super();
    }

    public CommentDetailDTO(ObjectId id, String text, LocalDateTime createdAt, ObjectId blogId, ObjectId userId, String username) {
        super(id, text, createdAt, blogId, userId);
        this.username = username;
    }
}
