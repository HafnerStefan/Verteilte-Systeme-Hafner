package ch.hftm.blog.dto.requerstDTO;

import java.util.List;

import ch.hftm.blog.entity.Comment;
import ch.hftm.blog.entity.User;
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

    @Schema(required = true, example = "xxxxx")
    private User user;

    @Nullable
    @Schema(example = "[xxxx]")
    private List<Comment> comment;

    public BlogRequest() {
    }

    public BlogRequest(String title, String text, User user) {
        this.title = title;
        this.text = text;
        this.user = user;
    }

    public BlogRequest(String title, String text, User user, List<Comment> comment) {
        this.title = title;
        this.text = text;
        this.user = user;
        this.comment = comment;
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

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Nullable
    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(@Nullable List<Comment> comment) {
        this.comment = comment;
    }


}
