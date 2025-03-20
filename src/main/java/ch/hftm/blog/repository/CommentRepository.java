package ch.hftm.blog.repository;

import java.time.LocalDateTime;
import java.util.List;

import ch.hftm.blog.entity.Comment;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CommentRepository implements PanacheRepository<Comment> {

    public long countByBlogId(Long blogId) {
        return count("blog.id", blogId);
    }

}
