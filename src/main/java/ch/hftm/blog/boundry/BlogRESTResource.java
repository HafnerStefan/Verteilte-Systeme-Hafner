package ch.hftm.blog.boundry;

import ch.hftm.blog.control.CommentService;
import ch.hftm.blog.dto.*;
import ch.hftm.blog.dto.mapper.BlogMapper;
import ch.hftm.blog.dto.requerstDTO.PaginationParams;
import ch.hftm.blog.dto.responseDTO.PaginationResponse;
import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.Comment;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import ch.hftm.blog.control.BlogService;
import ch.hftm.blog.dto.requerstDTO.BlogRequest;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("blog")
// Unter welchem Web-Pfad die Ressource erreichbar ist. Diese Annotation darfst du zusätzlich auch direkt über der Methode anbringen
@ApplicationScoped
public class BlogRESTResource {

	@Inject
	BlogService blogService;

	@Inject
	CommentService commentService;

	@GET
	@RolesAllowed({"User", "Admin"})
	@Produces(MediaType.APPLICATION_JSON)
	@APIResponse(responseCode = "200", description = "List of blogs", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = BlogListDTO[].class)))
	public Response getBlogs(PaginationParams paginationParams) {
		PaginationResponse<Blog> blogs = blogService.getBlogs(paginationParams);
		PaginationResponse<BlogListDTO> blogDTOs = new PaginationResponse<>(
				blogs.getContent().stream().map(BlogMapper::toBlogListDTO).toList(),
				blogs.getTotalElements(),
				blogs.getPage(),
				blogs.getSize()
		);
		Log.info("Returning " + blogDTOs.getContent().size() + " users");
		return Response.ok(blogDTOs).build();
	}

	@GET
	@Path("/{blogId}")
	@RolesAllowed({"User", "Admin"})
	@Produces(MediaType.APPLICATION_JSON)
	@APIResponses({
			@APIResponse(responseCode = "200", description = "Blog found", content = @Content(schema = @Schema(implementation = BlogDetailsDTO.class))),
			@APIResponse(responseCode = "404", description = "Blog not found")
	})
	// FETCH BLOG BY ID
	public Response getBlogById(@PathParam("blogId") Long id) {
		BlogDetailsDTO blogDetailsDTO = BlogMapper.toBlogDetailsDTO(blogService.getBlogById(id));
		if (blogDetailsDTO == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		Log.info("Returning blog with ID: " + id);
		return Response.ok(blogDetailsDTO).build();

	}


	//TODO Remove?
	@GET
	@Path("/toUser/blogId:{blogId}/userId:{userId}")
	@RolesAllowed({ "Admin"})
	@APIResponses({
			@APIResponse(responseCode = "200", description = "User assigned to blog", content = @Content(schema = @Schema(implementation = UserBaseDTO.class))),
			@APIResponse(responseCode = "404", description = "Blog or User not found")
	})
	// USER TO BLOG
	public Response assignUserToBlog(@PathParam("blogId") Long blogId, @PathParam("userId") Long userId) {
		BlogBaseDTO updatedBlogBaseDTO = BlogMapper.toBlogBaseDTO(blogService.addUserToBlog(blogId, userId));
		Log.info("User with ID: " + userId + " assigned to blog with ID: " + blogId);
		return Response.ok(updatedBlogBaseDTO).build();
	}

	@POST
	@RolesAllowed({"User", "Admin"})
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@APIResponse(responseCode = "201", description = "Blog created", content = @Content(schema = @Schema(implementation = BlogBaseDTO.class)))
	// CREATE NEW BLOG
	public Response createNewBlog(BlogRequest blogRequest) {
		BlogBaseDTO createdBlog = BlogMapper.toBlogBaseDTO(blogService.addBlog(blogRequest));
		Log.info("Blog created with ID: " + createdBlog.getId());
		return Response.status(Response.Status.CREATED).entity(createdBlog).build();
	}

	@PUT
	@Path("/{blogId}")
	@RolesAllowed({"User", "Admin"})
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@APIResponses({
			@APIResponse(responseCode = "200", description = "Blog updated", content = @Content(schema = @Schema(implementation = BlogBaseDTO.class))),
			@APIResponse(responseCode = "404", description = "Blog not found")
	})
	//Update Blog with comments
	public Response updateBlog(@PathParam("blogId") Long id, BlogRequest blogRequest) {
		BlogBaseDTO updatedBlogBaseDTO = BlogMapper.toBlogBaseDTO(blogService.updateBlog(id, blogRequest));
		Log.info("Blog with ID: " + id + " updated");
		return Response.ok(updatedBlogBaseDTO).build();

	}

	@DELETE
	@Path("/{blogId}")
	@RolesAllowed({"User", "Admin"})
	@Produces(MediaType.APPLICATION_JSON)
	@APIResponses({
			@APIResponse(responseCode = "200", description = "Blog deleted", content = @Content(schema = @Schema(implementation = BlogDetailsDTO.class))),
			@APIResponse(responseCode = "404", description = "Blog not found")
	})
	// REMOVE BLOG
	public Response removeBlog(@PathParam("blogId") Long id) {
		BlogBaseDTO blogDetailsDTO = BlogMapper.toBlogBaseDTO(blogService.getBlogById(id));
		this.blogService.deleteBlogbyId(id);
		Log.info("Blog with ID: " + id + " deleted");
		return Response.ok(blogDetailsDTO).build();
	}
}
