package ch.hftm.blog.repository;

import java.time.LocalDateTime;
import java.util.List;

import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.Comment;
import ch.hftm.blog.entity.User;
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

    public List<Comment> findPreviousComments(Long blogId, LocalDateTime createdAt, int limit) {
        return find("blog.id = ?1 and createdAt < ?2 order by createdAt desc", blogId, createdAt).page(0, limit).list();
    }

    public List<Comment> findNextComments(Long blogId, LocalDateTime createdAt, int limit) {
        return find("blog.id = ?1 and createdAt > ?2 order by createdAt asc", blogId, createdAt).page(0, limit).list();
    }

/*    // Additional deletion methods for comments
    @Transactional
    public void deleteByUser(User user) {
        getEntityManager().createQuery("DELETE FROM Comment c WHERE c.user = :user")
                .setParameter("user", user)
                .executeUpdate();
    }

    @Transactional
    public void deleteByBlog(Blog blog) {
        getEntityManager().createQuery("DELETE FROM Comment c WHERE c.blog = :blog")
                .setParameter("blog", blog)
                .executeUpdate();
    }*/


}




