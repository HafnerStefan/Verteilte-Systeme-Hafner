package ch.hftm.blog.repository;

import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.Comment;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import org.bson.types.ObjectId;
import dev.morphia.Datastore;
import dev.morphia.query.FindOptions;
import dev.morphia.query.Sort;
import dev.morphia.query.filters.Filters;

@ApplicationScoped
public class BlogRepository {

    @Inject
    Datastore datastore; // Morphia Datastore für MongoDB

    public void save(Blog blog) {
        datastore.save(blog);
    }

    public List<Blog> findAll() {
        return findAll(new FindOptions());
    }

    public List<Blog> findAll(FindOptions options) {
        return datastore.find(Blog.class).iterator(options).toList();
    }

    public Blog findById(ObjectId id) {
        return datastore.find(Blog.class)
                .filter(Filters.eq("_id", id))
                .first();
    }

    public Blog findByTitle(String title) {
        return datastore.find(Blog.class)
                .filter(Filters.eq("title", title))
                .first();
    }

    public List<Blog> findByUserId(ObjectId userId, FindOptions options) {
        return datastore.find(Blog.class)
                .filter(Filters.eq("user", userId))
                .iterator(options).toList();
    }

    public void deleteById(ObjectId id) {
        datastore.find(Blog.class)
                .filter(Filters.eq("_id", id))
                .delete();
    }

    public long count() {
        return datastore.find(Blog.class).count();
    }
}
