package ch.hftm.blog.control;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import ch.hftm.blog.dto.CommentBaseDTO;
import ch.hftm.blog.dto.CommentWithBlogContextDTO;
import ch.hftm.blog.dto.CommentWithBlogTitleDTO;
import ch.hftm.blog.dto.mapper.CommentMapper;
import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.Comment;
import ch.hftm.blog.entity.User;
import ch.hftm.blog.exception.ObjectNotFoundException;
import ch.hftm.blog.repository.BlogRepository;
import ch.hftm.blog.repository.CommentRepository;
import ch.hftm.blog.repository.UserRepository;
import io.quarkus.logging.Log;
import io.quarkus.security.UnauthorizedException;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;

// For Mongo DB
import org.bson.types.ObjectId;

@Dependent
public class CommentService {
	@Inject
	CommentRepository commentRepository;

	@Inject
	BlogRepository blogRepository;

	@Inject
	UserRepository userRepository;
	@Inject
	JsonWebToken jwtToken;


	public List<CommentBaseDTO> getComments() {
		var comments = commentRepository.findAll();
		Log.info("Returning " + comments.size() + " comments");
		return comments.stream().map(CommentMapper::toCommentBaseDTO).collect(Collectors.toList());
	}

	public CommentBaseDTO getCommentDTOById(ObjectId commentId) {
		Comment comment = getCommentById(commentId);
		return CommentMapper.toCommentBaseDTO(comment);
	}

	public Comment getCommentById(ObjectId commentId) {
		Comment comment = commentRepository.findById(commentId);
		if (comment != null) {
			return comment;
		} else {
			throw new ObjectNotFoundException("Comment with id " + commentId + " not found");
		}
	}

	public int getMaxCommentPageByBlogId(ObjectId blogId, int pageSize) {
		long totalComments = commentRepository.countByBlogId( blogId);
		return (int) Math.ceil((double) totalComments / pageSize);
	}


	public List<CommentWithBlogTitleDTO> getCommentsWithBlogTitleByUserId(ObjectId userId) {
		User user  = userRepository.findById(userId);
		if (user == null) {
			throw new ObjectNotFoundException("User not found with ID: " + userId);
		}
		List<Comment> comments = commentRepository.findByUserId(userId);
		if (comments == null || comments.isEmpty()) {
			throw new ObjectNotFoundException("No comments found for user with id " + userId);
		} else {

			return comments.stream().map(CommentMapper::toCommentWithBlogTitleDTO).collect(Collectors.toList());
		}
	}

	public CommentWithBlogContextDTO getCommentWithBlogContextById(ObjectId commentId,int previousCommentSize, int nextCommentSize) {
		Comment comment = getCommentById(commentId);
		List<Comment> previousComments = commentRepository.findPreviousComments(comment.getBlog().getId(), comment.getCreatedAt(), previousCommentSize);
		List<Comment> nextComments = commentRepository.findNextComments(comment.getBlog().getId(), comment.getCreatedAt(), nextCommentSize);
		return CommentMapper.toCommentWithBlogContextDTO(comment, previousComments, nextComments);
	}



	public List<CommentBaseDTO> getCommentsByBlogId(ObjectId blogId) {
		List<Comment> comments = commentRepository.findByBlogId(blogId);
		if (comments != null && !comments.isEmpty()) {
			return comments.stream().map(CommentMapper::toCommentBaseDTO).collect(Collectors.toList());
		} else {
			throw new ObjectNotFoundException("No comments found for blog with id " + blogId);
		}
	}

	public List<CommentBaseDTO> getCommentsByUserId(ObjectId userId) {
		List<Comment> comments = commentRepository.findByUserId(userId);
		if (comments != null && !comments.isEmpty()) {
			return comments.stream().map(CommentMapper::toCommentBaseDTO).collect(Collectors.toList());
		} else {
			throw new ObjectNotFoundException("No comments found for user with id " + userId);
		}
	}



	public CommentBaseDTO addComment(CommentBaseDTO commentBaseDTO) {
		Comment comment = CommentMapper.toComment(commentBaseDTO);
		Blog blog = blogRepository.findById(commentBaseDTO.getBlogId());
		User user = userRepository.findById(commentBaseDTO.getUserId());
		if (blog == null && user == null) {
			throw new ObjectNotFoundException("Blog with id " + commentBaseDTO.getBlogId() + " and User not found with ID: " + commentBaseDTO.getUserId() + " not found");
		} else if (blog == null) {
			throw new ObjectNotFoundException("Blog with id " + commentBaseDTO.getBlogId() + " not found");
		} else if (user == null) {
			throw new ObjectNotFoundException("User not found with ID: " + commentBaseDTO.getUserId());
		} else {
			comment.setBlog(blog);
			comment.setUser(user);
			comment.setCreatedAt(LocalDateTime.now());
			Log.info("Adding Comment by User " + comment.getUser().getName());
			commentRepository.save(comment);
			return CommentMapper.toCommentBaseDTO(comment);
		}
	}


	public void deleteComment(ObjectId commentId) {
		Comment comment = commentRepository.findById(commentId);
		if (comment == null) {
			throw new ObjectNotFoundException("Comment with id " + commentId + " not found");
		}

		// Überprüfen, ob der aktuelle Benutzer berechtigt ist, diesen Kommentar zu löschen
		ObjectId currentUserId = new ObjectId(jwtToken.getSubject());
		Set<String> roles = jwtToken.getGroups();

		if (comment.getUser().getId() != currentUserId && !roles.contains("Admin")) {
			throw new UnauthorizedException("You are not allowed to delete this comment");
		}

		Blog blog = comment.getBlog();
		User user = comment.getUser();

		if (blog != null) {
			blog.getComments().remove(comment);
		}
		if (user != null) {
			user.getComments().remove(comment);
		}
		commentRepository.delete(comment);

		Log.info("Deleting Comment by User " + comment.getUser().getName());
	}





}
