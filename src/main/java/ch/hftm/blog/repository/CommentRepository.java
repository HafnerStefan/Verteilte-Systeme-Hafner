package ch.hftm.blog.repository;

import java.util.List;

import ch.hftm.blog.entity.Comment;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CommentRepository implements PanacheRepository<Comment> {

    // Panache stellt die Methoden listAll, findById, persist, count und delete zur Verfügung

    @Transactional
    public List<Comment> findByBlogId(Long blogId) {
        return find("blog.id", blogId).list();
    }

    @Transactional
    public List<Comment> findByUserId(Long userId) {
        return find("user.id", userId).list();
    }
}
