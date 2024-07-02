package ch.hftm.blog.control;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import ch.hftm.blog.dto.CommentDTO;
import ch.hftm.blog.dto.mapper.CommentMapper;
import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.Comment;
import ch.hftm.blog.entity.User;
import ch.hftm.blog.exception.ObjectNotFoundException;
import ch.hftm.blog.repository.BlogRepository;
import ch.hftm.blog.repository.CommentRepository;
import ch.hftm.blog.repository.UserRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Dependent
public class CommentService {
	@Inject
	CommentRepository commentRepository;
	@Inject
	BlogRepository blogRepository;
	@Inject
	UserRepository userRepository;

	public List<CommentDTO> getComments() {
		var comments = commentRepository.listAll();
		Log.info("Returning " + comments.size() + " comments");
		return comments.stream().map(CommentMapper::toCommentDTO).collect(Collectors.toList());
	}

	public CommentDTO getCommentDTOById(Long commentId) {
		Comment comment = getCommentById(commentId);
		return CommentMapper.toCommentDTO(comment);
	}

	public Comment getCommentById(Long commentId) {
		Comment comment = commentRepository.findById(commentId);
		if (comment != null) {
			return comment;
		} else {
			throw new ObjectNotFoundException("Comment with id " + commentId + " not found");
		}
	}

	public List<CommentDTO> getCommentsByBlogId(Long blogId) {
		List<Comment> comments = commentRepository.findByBlogId(blogId);
		if (comments != null) {
			return comments.stream().map(CommentMapper::toCommentDTO).collect(Collectors.toList());
		} else {
			throw new ObjectNotFoundException("Comment with id " + blogId + " not found");
		}

	}

	public List<CommentDTO> getCommentsByUserId(Long userId) {
		List<Comment> comments = commentRepository.findByUserId(userId);
		if (comments != null) {
			return comments.stream().map(CommentMapper::toCommentDTO).collect(Collectors.toList());
		} else {
			throw new ObjectNotFoundException("Comment with id " + userId + " not found");
		}
	}

	@Transactional
	public CommentDTO addComment(CommentDTO commentDTO) {
		Comment comment = CommentMapper.toComment(commentDTO);
		Blog blog = blogRepository.findById(commentDTO.getBlogId());
		User user = userRepository.findById(commentDTO.getUserId());
		comment.setBlog(blog);
		comment.setUser(user);
		comment.setCreatedAt(LocalDateTime.now());
		Log.info("Adding Comment by User " + comment.getUser().getName());
		commentRepository.persist(comment);
		return CommentMapper.toCommentDTO(comment);
	}

	@Transactional
	public void deleteComment(Long commentId) {
		Comment comment = commentRepository.findById(commentId);
		if (comment == null){
			throw new ObjectNotFoundException("Comment with id " + comment + " not found");
		}
		Log.info("Deleting Comment by User " + comment.getUser().getName());
		commentRepository.delete(comment);
	}
}