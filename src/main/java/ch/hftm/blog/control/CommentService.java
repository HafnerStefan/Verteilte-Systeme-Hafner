package ch.hftm.blog.control;

import java.time.LocalDateTime;
import java.util.List;

import ch.hftm.blog.entity.Comment;
import ch.hftm.blog.exception.ObjectNotFoundException;
import ch.hftm.blog.repository.CommentRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Dependent
public class CommentService {
        @Inject
        CommentRepository commentRepository;

        public List<Comment> getComments() {
                var comments = commentRepository.listAll();
                Log.info("Returning " + comments.size() + " comments");
                return comments;
        }

        public Comment getCommentById(Long commentId) {
                Comment comment = commentRepository.findById(commentId);
                if (comment != null) {
                        return comment;
                } else {
                        throw new ObjectNotFoundException("Comment with id " + commentId + " not found");
                }
        }

        public List<Comment> getCommentsByBlogId(Long blogId) {
                return commentRepository.findByBlogId(blogId);
        }

        public List<Comment> getCommentsByUserId(Long userId) {
                return commentRepository.findByUserId(userId);
        }

        @Transactional
        public void addComment(Comment comment) {
                Log.info("Adding Comment by User " + comment.getUser().getName());
                comment.setCreatedAt(LocalDateTime.now());
                commentRepository.persist(comment);
        }

        @Transactional
        public void updateComment(Long id, Comment commentDetails) {
                Comment comment = getCommentById(id);
                comment.setText(commentDetails.getText());
                Log.info("Updating Comment by User " + comment.getUser().getName());
                commentRepository.persist(comment);
        }

        @Transactional
        public void deleteComment(Long commentId) {
                Comment comment = getCommentById(commentId);
                Log.info("Deleting Comment by User " + comment.getUser().getName());
                commentRepository.delete(comment);
        }
}