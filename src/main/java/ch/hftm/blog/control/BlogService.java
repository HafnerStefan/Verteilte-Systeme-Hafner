package ch.hftm.blog.control;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import ch.hftm.blog.dto.BlogBaseDTO;
import ch.hftm.blog.dto.BlogDetailsDTO;
import ch.hftm.blog.dto.BlogListDTO;
import ch.hftm.blog.dto.mapper.BlogMapper;
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
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Dependent
public class BlogService {
	@Inject
	BlogRepository blogRepository;
	@Inject
	UserRepository userRepository;
	@Inject
	CommentRepository commentRepository;


	public List<BlogListDTO> getBlogs(int start, int size) {
		List<Blog> blogs = blogRepository.findAll().page(Page.of(start, size)).list();
		return blogs.stream().map(BlogMapper::toBlogListDTO).collect(Collectors.toList());
	}

	public BlogDetailsDTO getBlogDetailsDTOById(Long blogId, int commentStart, int commentSize, boolean sortByDateAsc) {
		Blog blog = getBlogById(blogId);
		List<Comment> comments;
		if (sortByDateAsc) {
			comments = commentRepository.find("blog.id", Sort.by("createdAt").ascending(), blogId).page(Page.of(commentStart, commentSize)).list();
		} else {
			comments = commentRepository.find("blog.id", Sort.by("createdAt").descending(), blogId).page(Page.of(commentStart, commentSize)).list();
		}
		return BlogMapper.toBlogDetailsDTO(blog, comments);
	}

	public BlogDetailsDTO getBlogDetailsDTOById(Long blogId) {
		Blog blog = getBlogById(blogId);
		List<Comment> comments;
		comments = commentRepository.find("blog.id", Sort.by("createdAt").ascending(), blogId).list();
		return BlogMapper.toBlogDetailsDTO(blog, comments);
	}

	public BlogBaseDTO getBlogBaseDTOById(Long blogId) {
		Blog blog = getBlogById(blogId);
		return BlogMapper.toBlogBaseDTO(blog);
	}

	public List<BlogListDTO> getBlogsByUserId(Long userId, int start, int size) {
		List<Blog> blogs = blogRepository.find("user.id", userId).page(Page.of(start, size)).list();
		if (blogs != null) {
			return blogs.stream().map(BlogMapper::toBlogListDTO).collect(Collectors.toList());
		} else {
			throw new ObjectNotFoundException("Blogs with User id: " + userId + " not found");
		}
	}


	public Blog getBlogById(Long blogId) {
		Blog blog = blogRepository.findById(blogId);
		if (blog != null) {
			return blog;
		} else {
			throw new ObjectNotFoundException("Blog with id " + blogId + " not found");
		}
	}

/*    public BlogDetailsDTO getBlogsByTitle(String title) {
        Blog blog = blogRepository.findByTitle(title);
        if (blog != null) {
            return BlogMapper.toBlogDetailsDTO(blog);
        } else {
            throw new ObjectNotFoundException("Blog with title: " + title + " not found");
        }
    }*/


	@Transactional
	public BlogBaseDTO addBlog(BlogBaseDTO blogBaseDTO) {
		Blog blog = BlogMapper.toBlog(blogBaseDTO);
		User user = userRepository.findById(blogBaseDTO.getUserId());
		if (user == null) {
			throw new ObjectNotFoundException("User with id "+ blogBaseDTO.getUserId()+ " doesn't exist.");
		} else {
		blog.setUser(user);
		blog.setCreatedAt(LocalDateTime.now());
		blog.setUpdatedAt(LocalDateTime.now());
		blogRepository.persist(blog);
		Log.info("Adding Blog " + blogBaseDTO.getTitle());
		return BlogMapper.toBlogBaseDTO(blog);
	}
	}

	@Transactional
	public BlogBaseDTO addUserToBlog(Long blogId, Long userId) {
		Blog blog = blogRepository.findById(blogId);
		User user = userRepository.findById(userId);
		if (blog == null && user == null) {
			throw new ObjectNotFoundException("Blog with id " + blogId + " and User not found with ID: " + userId + "not found");
		} else if (blog == null) {
			throw new ObjectNotFoundException("Blog with id " + blogId + " not found");
		} else if (user == null) {
			throw new ObjectNotFoundException("User not found with ID: " + userId);
		} else {
			blog.setUser(user);
			blogRepository.persist(blog);
			return BlogMapper.toBlogBaseDTO(blog);
		}
	}

	@Transactional
	public BlogListDTO updateBlog(Long id, BlogBaseDTO blogBaseDTO) {

		Blog blog = blogRepository.findById(id);

		blog.setTitle(blogBaseDTO.getTitle());
		blog.setText(blogBaseDTO.getText());
		blog.setUpdatedAt(LocalDateTime.now());
		Log.info("Updating Blog " + blog.getTitle());
		blogRepository.persist(blog);
		return BlogMapper.toBlogListDTO(blog);
	}

	@Transactional
	public void deleteBlog(Long blogId) {
		Blog blog = blogRepository.findById(blogId);
		if (blog == null) {
			throw new ObjectNotFoundException("Blog with id " + blogId + " not found");
		}
		User user = blog.getUser();

		for (Comment comment : blog.getComments()) {
			if (comment.getUser() != null) {
				comment.getUser().getComments().remove(comment);
				//userRepository.persistAndFlush(comment.getUser());
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
