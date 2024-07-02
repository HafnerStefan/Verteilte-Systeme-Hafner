package ch.hftm.blog.dto.requerstDTO;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;

import java.util.List;

@Setter
@Getter
@Schema(name = "BlogRequest", description = "Request payload for creating or updating a blog")
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

}
