package ch.hftm.blog.dto.requerstDTO;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Setter
@Getter
@Schema(name = "CommentRequest", description = "Request payload for creating comment")
public class CommentRequest {
    @Schema(required = true, example = "Great post!")
    private String text;
    @Schema(example = "[12345, 67890, 13579]")
    private Long userId;
    @Schema(example = "[12345, 67890, 13579]")
    private Long blogId;


    public CommentRequest() {
    }


    public CommentRequest(String text, Long userId, Long blogId) {
        this.text = text;
        this.userId = userId;
        this.blogId = blogId;
    }

}
