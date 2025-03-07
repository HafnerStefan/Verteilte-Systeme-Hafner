package ch.hftm.blog.dto;
import java.time.LocalDateTime;
import java.util.List;
import org.eclipse.microprofile.openapi.annotations.media.Schema;


@Schema(name = "BlogDetailsResponse", description = "DTO for detailed blog view with comments and user details")
public class BlogDetailsDTO extends BlogBaseDTO {
	@Schema(description = "List of comment details")
	private List<CommentDetailDTO> comments;

	@Schema(description = "User details")
	private UserBaseDTO user;



	// Default constructor
	public BlogDetailsDTO() {
		super();
	}

	// Constructor with all fields
	public BlogDetailsDTO(Long id, String title, String text, LocalDateTime createdAt, LocalDateTime updatedAt,
			Long userId,
			List<CommentDetailDTO> comments, UserBaseDTO user, String username) {
		super(id, title, text, createdAt, updatedAt, userId, username);
		this.comments = comments;
		this.user = user;

	}

	// Constructor with basic fields
	public BlogDetailsDTO(Long id, String title, String text, LocalDateTime createdAt, LocalDateTime updatedAt,
			Long userId,String username) {
		super(id, title, text, createdAt, updatedAt, userId, username);
	}

	// Getter and Setter

	public List<CommentDetailDTO> getComments() {
		return comments;
	}

	public void setComments(List<CommentDetailDTO> comments) {
		this.comments = comments;
	}

	public UserBaseDTO getUser() {
		return user;
	}

	public void setUser(UserBaseDTO user) {
		this.user = user;
	}
}
