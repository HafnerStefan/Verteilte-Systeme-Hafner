package ch.hftm.blog.dto;
import java.time.LocalDateTime;
import org.eclipse.microprofile.openapi.annotations.media.Schema;


@Schema(name = "CommentDetailResponse", description = "Detailed DTO for a comment, including user information")
public class CommentDetailDTO extends CommentBaseDTO {

    @Schema(description = "Username of the comment author", example = "johndoe")
    private String username;

    public CommentDetailDTO() {
        super();
    }

    public CommentDetailDTO(Long id, String text, LocalDateTime createdAt, Long blogId, Long userId, String username) {
        super(id, text, createdAt, blogId, userId);
        this.username = username;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }
}
