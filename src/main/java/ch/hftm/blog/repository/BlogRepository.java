package ch.hftm.blog.repository;

import java.util.List;

import ch.hftm.blog.entity.Blog;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class BlogRepository implements PanacheRepository<Blog> {

    public Blog findByTitle(String title) {
        return find("title", title).firstResult();
    }

    @Transactional
    public List<Blog> findByUserId(Long userId) {
        return find("user.id", userId).list();
    }

}
