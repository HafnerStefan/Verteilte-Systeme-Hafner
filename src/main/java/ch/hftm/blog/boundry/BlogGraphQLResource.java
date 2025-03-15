package ch.hftm.blog.boundry;

import ch.hftm.blog.control.BlogGraphQLService;
import ch.hftm.blog.control.BlogService;
import ch.hftm.blog.dto.requerstDTO.BlogRequest;
import ch.hftm.blog.dto.requerstDTO.PaginationParams;
import ch.hftm.blog.dto.responseDTO.PaginationResponse;
import ch.hftm.blog.entity.Blog;
import io.quarkus.logging.Log;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

@ApplicationScoped
@GraphQLApi
public class BlogGraphQLResource {

    @Inject
    BlogGraphQLService blogService;

    @Query("getAllBlog")
    @RolesAllowed({"User", "Admin"})
    @Description("Get all blogs")
    public PaginationResponse<Blog> getBlogs(PaginationParams paginationParams) {
        PaginationResponse<Blog> allBlog = blogService.getAllBlog(paginationParams);
        Log.info("Returning " + allBlog.getContent().size() + " blogs");
        return allBlog;
    }

    @Query("getBlogById")
    @RolesAllowed({"User", "Admin"})
    @Description("Get blog by ID")
    public Blog getBlogById(Long id) {
        Blog blog = blogService.getBlogById(id);
        Log.info("Returning blog with ID " + id);
        return blog;
    }

    @Query("assignUserToBlog")
    @RolesAllowed({"User", "Admin"})
    @Description("Assign user to blog")
    public Blog addUserToBlog(Long blogId, Long userId) {
        Blog blog = blogService.getBlogById(blogId);
        Log.info("Assigning user with ID " + userId + " to blog with ID " + blogId);
        return blog;
    }

    @Mutation("createBlog")
    @RolesAllowed({"User", "Admin"})
    @Description("Create blog")
    public Blog createBlog(BlogRequest blogRequest) {
        Blog blog = blogService.addBlog(blogRequest);
        Log.info("Creating blog with title " + blogRequest.getTitle());
        return blog;
    }

    @Mutation("deleteBlog")
    @RolesAllowed({"User", "Admin"})
    @Description("Delete blog by ID")
    public boolean removeBlog(Long id) {
        blogService.deleteBlogbyId(id);
        Log.info("Deleting blog with ID " + id);
        return true;
    }
}
