package ch.hftm.blog.control;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import ch.hftm.blog.dto.mapper.CommentMapper;
import ch.hftm.blog.dto.requerstDTO.CommentRequest;
import ch.hftm.blog.dto.requerstDTO.PaginationParams;
import ch.hftm.blog.dto.responseDTO.PaginationResponse;
import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.Comment;
import ch.hftm.blog.entity.User;
import ch.hftm.blog.exception.ObjectNotFoundException;
import ch.hftm.blog.producer.CommentNotificationProducer;
import ch.hftm.blog.repository.BlogRepository;
import ch.hftm.blog.repository.CommentRepository;
import ch.hftm.blog.repository.UserRepository;


import io.quarkus.logging.Log;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import io.quarkus.security.UnauthorizedException;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.jwt.JsonWebToken;

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

    @Inject
    CommentNotificationProducer commentNotificationProducer;

    public PaginationResponse<Comment> getComments(Long blogId, PaginationParams paginationRequest) {
        int page = paginationRequest.page;
        int size = paginationRequest.size;
        String sortOrder = paginationRequest.sortOrder;

        List<Comment> comments;

        if ("desc".equalsIgnoreCase(sortOrder)) {
            comments = commentRepository.find(
                            "blog.id = ?1",Sort.by("createdAt").descending(), blogId)
                    .page(Page.of(page, size))
                    .list();
        } else {
            comments = commentRepository.find(
                            "blog.id = ?1", Sort.by("createdAt").ascending(), blogId)
                    .page(Page.of(page, size))
                    .list();
        }
        long totalElements = blogRepository.count();
        Log.info("Fetched " + comments.size() + " blogs from page " + page);

        return new PaginationResponse<>(comments, totalElements, page, size);
    }

    public Comment getCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId);
        if (comment != null) {
            return comment;
        } else {
            throw new ObjectNotFoundException("Comment with id " + commentId + " not found");
        }
    }

    @Transactional
    public Comment addComment(CommentRequest commentRequest) {
        Blog blog = blogRepository.findById(commentRequest.getBlog().getId());
        User user = userRepository.findById(commentRequest.getUser().getId());
        if (blog == null && user == null) {
            throw new ObjectNotFoundException("Blog with id " + commentRequest.getBlog().getId() + " and User not found with ID: " + commentRequest.getBlog().getId() + " not found");
        } else if (blog == null) {
            throw new ObjectNotFoundException("Blog with id " + commentRequest.getBlog().getId() + " not found");
        } else if (user == null) {
            throw new ObjectNotFoundException("User not found with ID: " + commentRequest.getBlog().getId());
        } else {
            commentRequest.setBlog(blog);
            commentRequest.setUser(user);
            commentRequest.setCreatedAt(LocalDateTime.now());
            Comment comment = CommentMapper.toComment(commentRequest);
            Log.info("Adding Comment by User " + commentRequest.getUser().getName());
            commentRepository.persist(comment);

            // Kafa-Notification
            commentNotificationProducer.sendNotification(user.getEmail(), comment.getId(), blog.getTitle(), comment.getText());

            return comment;
        }
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId);
        if (comment == null) {
            throw new ObjectNotFoundException("Comment with id " + commentId + " not found");
        }

        // Überprüfen, ob der aktuelle Benutzer berechtigt ist, diesen Kommentar zu löschen
        long currentUserId = Long.parseLong(jwtToken.getSubject());
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

