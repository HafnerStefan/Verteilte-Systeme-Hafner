package ch.hftm.blog.control;

import java.time.LocalDateTime;
import java.util.List;

import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.User;
import ch.hftm.blog.exception.ObjectNotFoundException;
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
            throw new ObjectNotFoundException("Blog with id " + blogId + " not found");
        }
    }

    public Blog getBlogsByTitle(String title) {
        Blog blog = blogRepository.findByTitle(title);
        if (blog != null) {
            return blog;
        } else {
            throw new ObjectNotFoundException("Blog with title: " + title + " not found");
        }
    }

    public List<Blog> getBlogsByUserId(Long userId) {
        List<Blog> blog = blogRepository.findByUserId(userId);
        if (blog != null) {
            return blog;
        } else {
            throw new ObjectNotFoundException("Blogs with User id: " + userId + " not found");
        }
    }

    @Transactional
    public void addBlog(Blog blog) {
        Log.info("Adding Blog " + blog.getTitle());
        blog.setCreatedAt(LocalDateTime.now());
        blog.setUpdatedAt(LocalDateTime.now());
        blogRepository.persist(blog);
    }

    @Transactional
    public void updateBlog(Long id, Blog blogDetails) {
        Blog blog = getBlogById(id);
        blog.setTitle(blogDetails.getTitle());
        blog.setText(blogDetails.getText());
        blog.setUpdatedAt(LocalDateTime.now());
        Log.info("Updating Blog " + blog.getTitle());
        blogRepository.persist(blog);
    }

    @Transactional
    public void deleteBlog(Long blogId) {
        Blog blog = getBlogById(blogId);
        Log.info("Deleting Blog " + blog.getTitle());
        blogRepository.delete(blog);

    }

    //TODO Kann evtl. gelöscht werden da ich nun beim Blog den User in Construcktor aufgenommen habe.... wird wohl für comments auch nicht benötigt
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
            throw new ObjectNotFoundException("Blog with id " + blogId + " not found");
        }
    }
}
