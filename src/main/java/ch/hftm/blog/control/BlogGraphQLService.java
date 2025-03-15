package ch.hftm.blog.control;

import ch.hftm.blog.dto.requerstDTO.BlogRequest;
import ch.hftm.blog.dto.requerstDTO.PaginationParams;
import ch.hftm.blog.dto.responseDTO.PaginationResponse;
import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.Comment;
import ch.hftm.blog.entity.User;
import ch.hftm.blog.exception.ObjectNotFoundException;
import ch.hftm.blog.repository.BlogRepository;
import ch.hftm.blog.repository.CommentRepository;
import ch.hftm.blog.repository.UserRepository;
import io.quarkus.logging.Log;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import io.quarkus.security.UnauthorizedException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class BlogGraphQLService {

    @Inject
    BlogRepository blogRepository;
    @Inject
    UserRepository userRepository;
    @Inject
    CommentRepository commentRepository;
    @Inject
    JsonWebToken jwtToken;


    public PaginationResponse<Blog> getAllBlog(PaginationParams paginationRequest) {
        int page = paginationRequest.page;
        int size = paginationRequest.size;
        String sortOrder = paginationRequest.sortOrder;

        List<Blog> blogs;

        if ("desc".equalsIgnoreCase(sortOrder)) {
            blogs = blogRepository.findAll(Sort.by("updatedAt").descending())
                    .page(Page.of(page, size))
                    .list();
        } else {
            blogs = blogRepository.findAll(Sort.by("updatedAt").ascending())
                    .page(Page.of(page, size))
                    .list();
        }

        long totalElements = blogRepository.count();

        Log.info("Fetched " + blogs.size() + " blogs from page " + page);

        return new PaginationResponse<>(blogs, totalElements, page, size);
    }


    public Blog getBlogById(Long blogId) {
        Blog blog = blogRepository.findById(blogId);
        if (blog != null) {
            return blog;
        } else {
            throw new ObjectNotFoundException("Blog with id " + blogId + " not found");
        }
    }

    @Transactional
    public Blog addBlog(BlogRequest blogRequest) {
        Blog blog = new Blog();
        blog.setTitle(blogRequest.getTitle());
        blog.setText(blogRequest.getText());

        User user = userRepository.findById(blogRequest.getUser().getId());
        if (user == null) {
            throw new ObjectNotFoundException("User with id " + blogRequest.getUser().getId() + " doesn't exist.");
        } else {
            blog.setUser(user);
            blog.setCreatedAt(LocalDateTime.now());
            blog.setUpdatedAt(LocalDateTime.now());
            blogRepository.persist(blog);
            Log.info("Adding Blog " + blogRequest.getTitle());
            return blog;
        }
    }

    @Transactional
    public Blog addUserToBlog(Long blogId, Long userId) {
        Blog blog = blogRepository.findById(blogId);
        User user = userRepository.findById(userId);
        if (blog == null && user == null) {
            throw new ObjectNotFoundException(
                    "Blog with id " + blogId + " and User not found with ID: " + userId + "not found");
        } else if (blog == null) {
            throw new ObjectNotFoundException("Blog with id " + blogId + " not found");
        } else if (user == null) {
            throw new ObjectNotFoundException("User not found with ID: " + userId);
        } else {
            blog.setUser(user);
            blogRepository.persist(blog);
            return blog;
        }
    }

    @Transactional
    public Blog updateBlog(Long id, BlogRequest blogRequest) {
        Blog blog = blogRepository.findById(id);
        if (blog == null) {
            throw new ObjectNotFoundException("Blog with id " + id + " not found");
        }
        long currentUserId = Long.parseLong(jwtToken.getSubject());
        Set<String> roles = jwtToken.getGroups();
        if (blog.getUser().getId() != currentUserId && !roles.contains("Admin")) {
            throw new UnauthorizedException("You are not allowed to update this blog");
        }

        blog.setTitle(blogRequest.getTitle());
        blog.setText(blogRequest.getText());
        blog.setUpdatedAt(LocalDateTime.now());
        Log.info("Updating Blog " + blog.getTitle());
        blogRepository.persist(blog);
        return blog;
    }


    @Transactional
    public void deleteBlogbyId(Long blogId) {
        Blog blog = blogRepository.findById(blogId);
        if (blog == null) {
            throw new ObjectNotFoundException("Blog with id " + blogId + " not found");
        }
        // Überprüfen, ob der aktuelle Benutzer berechtigt ist, diesen Blog zu löschen
        long currentUserId = Long.parseLong(jwtToken.getSubject());
        Set<String> roles = jwtToken.getGroups();

        if (blog.getUser().getId() != currentUserId && !roles.contains("Admin")) {
            throw new UnauthorizedException("You are not allowed to delete this blog");
        }

        User user = blog.getUser();

        for (Comment comment : blog.getComments()) {
            if (comment.getUser() != null) {
                comment.getUser().getComments().remove(comment);
            }
            commentRepository.delete(comment);
        }

        if (user != null) {
            user.getBlogs().remove(blog);
        }
        blogRepository.delete(blog);

        if (user != null) {
            userRepository.persistAndFlush(user);
        }

        Log.info("Deleting Blog " + blog.getTitle());
    }


}
