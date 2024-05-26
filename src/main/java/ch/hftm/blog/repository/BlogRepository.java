package ch.hftm.blog.repository;

import ch.hftm.blog.entity.Blog;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BlogRepository implements PanacheRepository<Blog> {

    public Blog findByTitle(String title) {
        return find("title", title).firstResult();
    }

}
