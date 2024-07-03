package ch.hftm.blog.dto;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Schema(name = "BlogDetailsResponse", description = "DTO for detailed blog view with comments and user details")
public class BlogDetailsDTO extends BlogBaseDTO {
	@Schema(description = "List of comment details")
	private List<CommentBaseDTO> comments;

	@Schema(description = "User details")
	private UserBaseDTO user;

	// Default constructor
	public BlogDetailsDTO() {
		super();
	}

	// Constructor with all fields
	public BlogDetailsDTO(Long id, String title, String text, LocalDateTime createdAt, LocalDateTime updatedAt,
						  List<CommentBaseDTO> comments, UserBaseDTO user) {
		super(id, title, text, createdAt, updatedAt, null);
		this.comments = comments;
		this.user = user;
	}

	// Constructor with basic fields
	public BlogDetailsDTO(Long id, String title, String text, LocalDateTime createdAt, LocalDateTime updatedAt, Long userId) {
		super(id, title, text, createdAt, updatedAt, userId);
	}
}
