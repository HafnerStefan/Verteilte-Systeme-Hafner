package ch.hftm.blog.dto;

import java.time.LocalDateTime;
import java.util.List;
import org.eclipse.microprofile.openapi.annotations.media.Schema;


@Schema(name = "CommentWithBlogContextResponse", description = "DTO for comments with blog title and context")
public class CommentWithBlogContextDTO extends CommentBaseDTO {

	@Schema(required = true, example = "My Blog Title")
	private String blogTitle;
	@Schema(description = "Previous comments")
	private List<CommentBaseDTO> previousComments;
	@Schema(description = "Next comments")
	private List<CommentBaseDTO> nextComments;

	// Default constructor
	public CommentWithBlogContextDTO() {
		super();
	}

	// Constructor with parameters
	public CommentWithBlogContextDTO(Long id, String text, LocalDateTime createdAt, Long blogId, Long userId,
			String blogTitle,
			List<CommentBaseDTO> previousComments, List<CommentBaseDTO> nextComments) {
		super(id, text, createdAt, blogId, userId);
		this.blogTitle = blogTitle;
		this.previousComments = previousComments;
		this.nextComments = nextComments;
	}


	// Getter and Setter

	public String getBlogTitle() {
		return blogTitle;
	}

	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}

	public List<CommentBaseDTO> getPreviousComments() {
		return previousComments;
	}

	public void setPreviousComments(List<CommentBaseDTO> previousComments) {
		this.previousComments = previousComments;
	}

	public List<CommentBaseDTO> getNextComments() {
		return nextComments;
	}

	public void setNextComments(List<CommentBaseDTO> nextComments) {
		this.nextComments = nextComments;
	}
}
