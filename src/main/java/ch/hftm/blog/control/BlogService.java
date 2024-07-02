package ch.hftm.blog.control;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import ch.hftm.blog.dto.BlogDTO;
import ch.hftm.blog.dto.mapper.BlogMapper;
import ch.hftm.blog.dto.mapper.UserMapper;
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

    public List<BlogDTO> getBlogs() {
        var blogs = blogRepository.listAll();
        Log.info("Returning " + blogs.size() + " blogs");
        return blogs.stream()
                .map(BlogMapper::toBlogDTO)
                .collect(Collectors.toList());
    }

    public BlogDTO getBlogDTOById(Long blogId) {
        Blog blog = getBlogById(blogId);
        return BlogMapper.toBlogDTO(blog);
    }
    public Blog getBlogById(Long blogId) {
        Blog blog = blogRepository.findById(blogId);
        if (blog != null) {
            return blog;
        } else {
            throw new ObjectNotFoundException("Blog with id " + blogId + " not found");
        }
    }

    public BlogDTO getBlogsByTitle(String title) {
        Blog blog = blogRepository.findByTitle(title);
        if (blog != null) {
            return BlogMapper.toBlogDTO(blog);
        } else {
            throw new ObjectNotFoundException("Blog with title: " + title + " not found");
        }
    }

    public List<BlogDTO> getBlogsByUserId(Long userId) {
        List<Blog> blogs = blogRepository.findByUserId(userId);
        if (blogs != null) {
            return blogs.stream().map(BlogMapper::toBlogDTO).collect(Collectors.toList());
        } else {
            throw new ObjectNotFoundException("Blogs with User id: " + userId + " not found");
        }
    }

    @Transactional
    public BlogDTO addBlog(BlogDTO blogDTO) {
        Log.info("Adding Blog " + blogDTO.getTitle());
        Blog blog = BlogMapper.toBlog(blogDTO);
        User user = userRepository.findById(blogDTO.getUserId());
        blog.setUser(user);
        blog.setCreatedAt(LocalDateTime.now());
        blog.setUpdatedAt(LocalDateTime.now());
        blogRepository.persist(blog);
        return BlogMapper.toBlogDTO(blog);
    }

    @Transactional
    public BlogDTO updateBlog(Long id, BlogDTO blogDTO) {

        Blog blog = blogRepository.findById(id);

        blog.setTitle(blogDTO.getTitle());
        blog.setText(blogDTO.getText());
        blog.setUpdatedAt(LocalDateTime.now());
        Log.info("Updating Blog " + blog.getTitle());
        blogRepository.persist(blog);
        return BlogMapper.toBlogDTO(blog);
    }

    @Transactional
    public void deleteBlog(Long blogId) {
        Blog blog = blogRepository.findById(blogId);
        if (blog == null) {
            throw new ObjectNotFoundException("Blog with id " + blogId + " not found");
        }
        Log.info("Deleting Blog " + blog.getTitle());
        blogRepository.delete(blog);

    }

    @Transactional
    public BlogDTO addUserToBlog(Long blogId, Long userId) {
        Blog blog = blogRepository.findById(blogId);
        if (blog == null) {
            throw new ObjectNotFoundException("Blog with id " + blogId + " not found");
        }
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new ObjectNotFoundException("User not found with ID: " + userId);
        }
        blog.setUser(user);
        blogRepository.persist(blog);
        return BlogMapper.toBlogDTO(blog);
    }
}
