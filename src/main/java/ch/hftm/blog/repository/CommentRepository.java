package ch.hftm.blog.repository;

import java.time.LocalDateTime;
import java.util.List;

import ch.hftm.blog.entity.Comment;
import ch.hftm.blog.entity.User;
import dev.morphia.query.FindOptions;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
// For Mongo DB
import org.bson.types.ObjectId;
import dev.morphia.Datastore;
import dev.morphia.query.filters.Filters;

@ApplicationScoped
public class CommentRepository  {

    @Inject
    Datastore datastore; // Morphia Datastore für MongoDB

    // Panache stellt die Methoden listAll, findById, persist, count und delete zur Verfügung

    public void save(Comment comment) {
        datastore.save(comment);
    }

    public void delete(Comment comment) {
        datastore.delete(comment);
    }


    public void deleteByUser(ObjectId userId) {
        datastore.find(Comment.class).filter(Filters.eq("user", userId)).delete();
    }

    public void deleteByBlog(ObjectId blogId) {
        datastore.find(Comment.class).filter(Filters.eq("blog", blogId)).delete();
    }

    public List<Comment> findAll() {
        return findAll(new FindOptions());
    }

    public List<Comment> findAll(FindOptions options) {
        return datastore.find(Comment.class).iterator(options).toList();
    }

    public Comment findById(ObjectId id) {
        return datastore.find(Comment.class).filter(Filters.eq("_id", id)).first();
    }

    public List<Comment> findByBlogId(ObjectId blogId) {
        return datastore.find(Comment.class).filter(Filters.eq("blog", blogId)).iterator().toList();
    }

    public List<Comment> findByBlogId(ObjectId blogId, FindOptions options) {
        return datastore.find(Comment.class).filter(Filters.eq("blog", blogId)).iterator(options).toList();
    }


    public List<Comment> findByUserId(ObjectId userId) {
        return datastore.find(Comment.class).filter(Filters.eq("user", userId)).iterator().toList();
    }

    public long countByBlogId(ObjectId blogId) {
        return datastore.find(Comment.class).filter(Filters.eq("blog", blogId)).count();
    }


    public List<Comment> findPreviousComments(ObjectId blogId, LocalDateTime createdAt, int limit) {
        return datastore.find(Comment.class)
                .filter(Filters.eq("blog", blogId), Filters.lt("createdAt", createdAt))
                .iterator().toList()
                .subList(0, (int) Math.min(limit, datastore.find(Comment.class).count()));
    }

    public List<Comment> findNextComments(ObjectId blogId, LocalDateTime createdAt, int limit) {
        return datastore.find(Comment.class)
                .filter(Filters.eq("blog", blogId), Filters.gt("createdAt", createdAt))
                .iterator().toList()
                .subList(0, (int) Math.min(limit, datastore.find(Comment.class).count()));
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
