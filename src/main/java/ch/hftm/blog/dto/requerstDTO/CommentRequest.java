package ch.hftm.blog.dto.requerstDTO;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Setter
@Getter
@Schema(name = "CommentRequest", description = "Request payload for creating comment")
public class CommentRequest {
    @Schema(required = true, example = "Great post!")
    private String text;
    @Schema(example = "12345")
    private ObjectId userId;
    @Schema(example = "67890")
    private ObjectId blogId;


    public CommentRequest() {
    }


    public CommentRequest(String text, ObjectId userId, ObjectId blogId) {
        this.text = text;
        this.userId = userId;
        this.blogId = blogId;
    }

}
