package ch.hftm.blog.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(name = "BlogDetailsResponse", description = "DTO for detailed blog view with comments and user details")
public class BlogDetailsDTO extends BlogBaseDTO {
	@Schema(description = "List of comment details")
	private List<CommentDetailDTO> comments;

	@Schema(description = "User details")
	private UserBaseDTO user;

	@Schema(description = "Username of the blog author")
	private String username;

	// Default constructor
	public BlogDetailsDTO() {
		super();
	}

	// Constructor with all fields
	public BlogDetailsDTO(Long id, String title, String text, LocalDateTime createdAt, LocalDateTime updatedAt,
			Long userId,
			List<CommentDetailDTO> comments, UserBaseDTO user, String username) {
		super(id, title, text, createdAt, updatedAt, userId);
		this.comments = comments;
		this.user = user;
		this.username = username;
	}

	// Constructor with basic fields
	public BlogDetailsDTO(Long id, String title, String text, LocalDateTime createdAt, LocalDateTime updatedAt,
			Long userId) {
		super(id, title, text, createdAt, updatedAt, userId);
	}
}
