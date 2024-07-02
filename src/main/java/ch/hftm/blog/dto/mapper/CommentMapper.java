package ch.hftm.blog.dto.mapper;

import ch.hftm.blog.dto.CommentDTO;
import ch.hftm.blog.entity.Comment;
import ch.hftm.blog.repository.BlogRepository;

public class CommentMapper {

    public static CommentDTO toCommentDTO(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getText(),
                comment.getCreatedAt(),
                comment.getBlog().getId(),
                comment.getUser().getId());
    }

    public static Comment toComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setText(commentDTO.getText());
        comment.setCreatedAt(commentDTO.getCreatedAt());

        // Blog and user are usually set separately
        return comment;
    }
}
