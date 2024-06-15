package ch.hftm.blog.control;

import java.util.List;

import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.User;
import ch.hftm.blog.repository.BlogRepository;
import ch.hftm.blog.repository.UserRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Dependent
public class BlogService {
    @Inject
    BlogRepository blogRepository;

    @Inject
    UserRepository userRepository;

    public List<Blog> getBlogs() {
        var blogs = blogRepository.listAll();
        Log.info("Returning " + blogs.size() + " blogs");
        return blogs;
    }

    public Blog getBlogById(Long blogId) {
        Blog blog = blogRepository.findById(blogId);
        if (blog != null) {
            return blog;
        } else {
            throw new BlogNotFoundException("Blog with id " + blogId + " not found");
        }
    }

    @Transactional
    public void addBlog(Blog blog) {
        Log.info("Adding blog " + blog.getTitle());
        blogRepository.persist(blog);

    }

    @Transactional
    public void deleteBlog(Long blogId) {
        Blog blog = blogRepository.findById(blogId);
        if (blog != null) {
            blogRepository.delete(blog);
        } else {
            throw new BlogNotFoundException("Blog with id " + blogId + " not found");
        }
    }

    @Transactional
    public void addUserToBlog(Long blogId, User user) {
        Blog blog = blogRepository.findById(blogId);
        if (blog != null) {
            if (user.getId() != null) {
                user = userRepository.getEntityManager().merge(user); // Verwende merge, wenn der Benutzer bereits existiert
            } else {
                userRepository.persist(user); // Verwende persist, wenn der Benutzer neu ist
            }
            blog.setUser(user); // Weise dann das User-Objekt dem Blog zu
            blogRepository.persist(blog); // Aktualisiere das Blog-Objekt in der Datenbank
        } else {
            throw new BlogNotFoundException("Blog with id " + blogId + " not found");
        }
    }
}
