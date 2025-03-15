package ch.hftm.blog.dto.requerstDTO;

import java.util.List;

import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import jakarta.annotation.Nullable;

@Schema(name = "BlogRequest", description = "Request payload for creating or updating a blog")
@Name("BlogRequest")
public class BlogRequest {
    @Schema(required = true, example = "New Blog")
    private String title;

    @Schema(required = true, example = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus laoreet eu purus ac congue. Proin aliquam in enim aliquet viverra.")
    private String text;

    @Schema(required = true, example = "2023-06-15T10:15:30")
    private Long userId;

    @Nullable
    @Schema(example = "[12345, 67890, 13579]")
    private List<Long> commentIds;

    public BlogRequest() {
    }

    public BlogRequest(String title, String text, Long userId) {
        this.title = title;
        this.text = text;
        this.userId = userId;

    }

    public BlogRequest(String title, String text, Long userId, List<Long> commentIds) {
        this.title = title;
        this.text = text;
        this.userId = userId;
        this.commentIds = commentIds;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Nullable
    public List<Long> getCommentIds() {
        return commentIds;
    }

    public void setCommentIds(@Nullable List<Long> commentIds) {
        this.commentIds = commentIds;
    }
}
