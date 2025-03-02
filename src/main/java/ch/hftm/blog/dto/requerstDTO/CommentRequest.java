package ch.hftm.blog.dto.requerstDTO;

import org.eclipse.microprofile.openapi.annotations.media.Schema;


@Schema(name = "CommentRequest", description = "Request payload for creating comment")
public class CommentRequest {
    @Schema(required = true, example = "Great post!")
    private String text;
    @Schema(example = "12345")
    private Long userId;
    @Schema(example = "67890")
    private Long blogId;


    public CommentRequest() {
    }


    public CommentRequest(String text, Long userId, Long blogId) {
        this.text = text;
        this.userId = userId;
        this.blogId = blogId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }
}
