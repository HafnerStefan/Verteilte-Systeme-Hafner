package ch.hftm.blog.dto.requerstDTO;

import java.util.List;

import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(name = "BlogRequest", description = "Request payload for creating or updating a blog")
public class BlogRequest {
    @Schema(required = true, example = "New Blog")
    private String title;
    @Schema(required = true, example = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus laoreet eu purus ac congue. Proin aliquam in enim aliquet viverra.")
    private String text;
    @Schema(required = true, example = "2023-06-15T10:15:30")
    private ObjectId userId;
    @Nullable
    @Schema(example = "[12345, 67890, 13579]")
    private List<ObjectId> commentIds;

    public BlogRequest() {
    }

    public BlogRequest(String title, String text, ObjectId userId) {
        this.title = title;
        this.text = text;
        this.userId = userId;

    }

    public BlogRequest(String title, String text, ObjectId userId, List<ObjectId> commentIds) {
        this.title = title;
        this.text = text;
        this.userId = userId;
        this.commentIds = commentIds;

    }

}
