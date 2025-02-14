package ch.hftm.blog.control;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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

import io.quarkus.security.UnauthorizedException;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

import org.eclipse.microprofile.jwt.JsonWebToken;

// For Mongo DB
import org.bson.types.ObjectId;
import dev.morphia.query.FindOptions;
import dev.morphia.query.Sort;


@Dependent
public class BlogService {
	@Inject
	BlogRepository blogRepository;
	@Inject
	UserRepository userRepository;
	@Inject
	CommentRepository commentRepository;
	@Inject
	JsonWebToken jwtToken;

	public List<BlogListDTO> getBlogs(int startPage, int size, String sortOrder) {
		Sort sort = "desc".equalsIgnoreCase(sortOrder) ? Sort.descending("updatedAt") : Sort.ascending("updatedAt");
		List<Blog> blogs = blogRepository.findAll(new FindOptions().sort(sort).skip(startPage * size).limit(size));
		Log.info("Fetched " + blogs.size() + " blogs from page " + startPage);
		return blogs.stream().map(BlogMapper::toBlogListDTO).collect(Collectors.toList());
	}

	public List<BlogListDTO> getBlogsByUserId(ObjectId userId, int startPage, int size, String sortOrder) {
		Sort sort = "desc".equalsIgnoreCase(sortOrder) ? Sort.descending("updatedAt") : Sort.ascending("updatedAt");
		List<Blog> blogs = blogRepository.findByUserId(userId, new FindOptions().sort(sort).skip(startPage * size).limit(size));
		Log.info("Fetched " + blogs.size() + " blogs for user with ID " + userId + " from page " + startPage);
		return blogs.stream().map(BlogMapper::toBlogListDTO).collect(Collectors.toList());
	}

	public int getTotalBlogCount() {
		return (int) blogRepository.count();
	}

	public int getMaxBlogPage(int blogSize) {
		long totalBlogs = blogRepository.count();
		return (int) Math.ceil((double) totalBlogs / blogSize);
	}

	public Blog getBlogById(ObjectId blogId) {
		Blog blog = blogRepository.findById(blogId);
		if (blog == null) {
			throw new ObjectNotFoundException("Blog with id " + blogId + " not found");
		}
		return blog;
	}

	public BlogDetailsDTO getBlogDetailsDTOById(ObjectId blogId, int commentStart, int commentSize, boolean sortByDateAsc) {
		Blog blog = getBlogById(blogId);

		Sort sort = sortByDateAsc ? Sort.ascending("createdAt") : Sort.descending("createdAt");

		List<Comment> comments = commentRepository.findByBlogId(
				blogId,
				new FindOptions().sort(sort).skip(commentStart * commentSize).limit(commentSize)
		);

		return BlogMapper.toBlogDetailsDTO(blog, comments);
	}


	public BlogDetailsDTO getBlogDetailsDTOById(ObjectId blogId) {
		Blog blog = getBlogById(blogId);
		List<Comment> comments;
		Sort sort = Sort.ascending("createdAt");
		comments = commentRepository.findByBlogId( blogId,new FindOptions().sort(sort));
		return BlogMapper.toBlogDetailsDTO(blog, comments);
	}

	public BlogBaseDTO getBlogBaseDTOById(ObjectId blogId) {
		Blog blog = this.getBlogById(blogId);
		return BlogMapper.toBlogBaseDTO(blog);
	}


	/*    public BlogDetailsDTO getBlogsByTitle(String title) {
	    Blog blog = blogRepository.findByTitle(title);
	    if (blog != null) {
	        return BlogMapper.toBlogDetailsDTO(blog);
	    } else {
	        throw new ObjectNotFoundException("Blog with title: " + title + " not found");
	    }
	}*/

	public BlogBaseDTO addBlog(BlogBaseDTO blogBaseDTO) {
		Blog blog = BlogMapper.toBlog(blogBaseDTO);
		User user = userRepository.findById(blogBaseDTO.getUserId());
		if (user == null) {
			throw new ObjectNotFoundException("User with id " + blogBaseDTO.getUserId() + " doesn't exist.");
		}
		blog.setUser(user);
		blog.setCreatedAt(LocalDateTime.now());
		blog.setUpdatedAt(LocalDateTime.now());
		blogRepository.save(blog);
		Log.info("Adding Blog " + blogBaseDTO.getTitle());
		return BlogMapper.toBlogBaseDTO(blog);
	}


	public BlogBaseDTO addUserToBlog(ObjectId blogId, ObjectId userId) {
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
			blogRepository.save(blog);
			return BlogMapper.toBlogBaseDTO(blog);
		}
	}

	public BlogListDTO updateBlog(ObjectId id, BlogBaseDTO blogBaseDTO) {
		Blog blog = blogRepository.findById(id);
		if (blog == null) {
			throw new ObjectNotFoundException("Blog with id " + id + " not found");
		}
		ObjectId currentUserId = new ObjectId(jwtToken.getSubject());
		Set<String> roles = jwtToken.getGroups();

		if (blog.getUser().getId() != currentUserId && !roles.contains("Admin")) {
			throw new UnauthorizedException("You are not allowed to update this blog");
		}

		blog.setTitle(blogBaseDTO.getTitle());
		blog.setText(blogBaseDTO.getText());
		blog.setUpdatedAt(LocalDateTime.now());
		Log.info("Updating Blog " + blog.getTitle());
		blogRepository.save(blog);
		return BlogMapper.toBlogListDTO(blog);
	}


	public void deleteBlog(ObjectId blogId) {
		Blog blog = blogRepository.findById(blogId);
		if (blog == null) {
			throw new ObjectNotFoundException("Blog with id " + blogId + " not found");
		}

		// Überprüfen, ob der aktuelle Benutzer berechtigt ist, diesen Blog zu löschen
		ObjectId currentUserId = new ObjectId(jwtToken.getSubject());
		Set<String> roles = jwtToken.getGroups();

		if (blog.getUser().getId() != currentUserId && !roles.contains("Admin")) {
			throw new UnauthorizedException("You are not allowed to delete this blog");
		}

		List<Comment> comments = commentRepository.findByBlogId(blogId);
		for (Comment comment : comments) {
			commentRepository.delete(comment);
		}

		User user = blog.getUser();

		if (user != null) {
			user.getBlogs().remove(blog);
			userRepository.save(user);
		}
		blogRepository.deleteById(blogId);

		Log.info("Deleting Blog " + blog.getTitle());
	}


/*
package ch.hftm.blog.control;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
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
import io.quarkus.security.UnauthorizedException;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.jwt.JsonWebToken;
import dev.morphia.query.Sort;
import dev.morphia.query.FindOptions;

@Dependent
public class BlogService {
    @Inject
    BlogRepository blogRepository;
    @Inject
    UserRepository userRepository;
    @Inject
    CommentRepository commentRepository;
    @Inject
    JsonWebToken jwtToken;

    public List<BlogListDTO> getBlogs(int startPage, int size, String sortOrder) {
        Sort sort = "desc".equalsIgnoreCase(sortOrder) ? Sort.descending("updatedAt") : Sort.ascending("updatedAt");
        List<Blog> blogs = blogRepository.findAll()
                .iterator(new FindOptions().sort(sort).skip(startPage * size).limit(size)).toList();
        Log.info("Fetched " + blogs.size() + " blogs from page " + startPage);
        return blogs.stream().map(BlogMapper::toBlogListDTO).collect(Collectors.toList());
    }

    public List<BlogListDTO> getBlogsByUserId(ObjectId userId, int startPage, int size, String sortOrder) {
        Sort sort = "desc".equalsIgnoreCase(sortOrder) ? Sort.descending("updatedAt") : Sort.ascending("updatedAt");
        List<Blog> blogs = blogRepository.findByUserId(userId, sort, startPage, size);
        Log.info("Fetched " + blogs.size() + " blogs for user with ID " + userId + " from page " + startPage);
        return blogs.stream().map(BlogMapper::toBlogListDTO).collect(Collectors.toList());
    }

    public int getTotalBlogCount() {
        return (int) blogRepository.count();
    }

    public int getMaxBlogPage(int blogSize) {
        long totalBlogs = blogRepository.count();
        return (int) Math.ceil((double) totalBlogs / blogSize);
    }

    public Blog getBlogById(ObjectId blogId) {
        return blogRepository.findById(blogId);
    }

    public BlogBaseDTO addBlog(BlogBaseDTO blogBaseDTO) {
        Blog blog = BlogMapper.toBlog(blogBaseDTO);
        User user = userRepository.findById(blogBaseDTO.getUserId());
        if (user == null) {
            throw new ObjectNotFoundException("User with id " + blogBaseDTO.getUserId() + " doesn't exist.");
        }
        blog.setUser(user);
        blog.setCreatedAt(LocalDateTime.now());
        blog.setUpdatedAt(LocalDateTime.now());
        blogRepository.save(blog);
        Log.info("Adding Blog " + blogBaseDTO.getTitle());
        return BlogMapper.toBlogBaseDTO(blog);
    }

    public BlogBaseDTO updateBlog(ObjectId id, BlogBaseDTO blogBaseDTO) {
        Blog blog = blogRepository.findById(id);
        if (blog == null) {
            throw new ObjectNotFoundException("Blog with id " + id + " not found");
        }
        ObjectId currentUserId = new ObjectId(jwtToken.getSubject());
        Set<String> roles = jwtToken.getGroups();
        if (!blog.getUser().getId().equals(currentUserId) && !roles.contains("Admin")) {
            throw new UnauthorizedException("You are not allowed to update this blog");
        }
        blog.setTitle(blogBaseDTO.getTitle());
        blog.setText(blogBaseDTO.getText());
        blog.setUpdatedAt(LocalDateTime.now());
        Log.info("Updating Blog " + blog.getTitle());
        blogRepository.save(blog);
        return BlogMapper.toBlogBaseDTO(blog);
    }

    public void deleteBlog(ObjectId blogId) {
        Blog blog = blogRepository.findById(blogId);
        if (blog == null) {
            throw new ObjectNotFoundException("Blog with id " + blogId + " not found");
        }
        ObjectId currentUserId = new ObjectId(jwtToken.getSubject());
        Set<String> roles = jwtToken.getGroups();
        if (!blog.getUser().getId().equals(currentUserId) && !roles.contains("Admin")) {
            throw new UnauthorizedException("You are not allowed to delete this blog");
        }
        for (Comment comment : blog.getComments()) {
            commentRepository.deleteById(comment.getId());
        }
        blogRepository.deleteById(blog.getId());
        Log.info("Deleting Blog " + blog.getTitle());
    }
}

 */
}
