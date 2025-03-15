package ch.hftm.blog.boundry;

import java.util.List;

import jakarta.annotation.security.RolesAllowed;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import jakarta.annotation.security.PermitAll;
import ch.hftm.blog.control.BlogService;
import ch.hftm.blog.control.UserService;
import ch.hftm.blog.dto.BlogBaseDTO;
import ch.hftm.blog.dto.BlogDetailsDTO;
import ch.hftm.blog.dto.BlogListDTO;
import ch.hftm.blog.dto.UserBaseDTO;
import ch.hftm.blog.dto.requerstDTO.BlogRequest;
import ch.hftm.blog.exception.ObjectIsEmptyException;
import io.quarkus.logging.Log;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("blog")
// Unter welchem Web-Pfad die Ressource erreichbar ist. Diese Annotation darfst du zusätzlich auch direkt über der Methode anbringen
@ApplicationScoped
public class BlogResource {

	@Inject
	BlogService blogService;

	@GET
	@RolesAllowed({"User", "Admin"})
	@Produces(MediaType.APPLICATION_JSON)
	@APIResponse(responseCode = "200", description = "List of blogs", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = BlogListDTO[].class)))
	public Response fetchAllBlogs(@QueryParam("userId") Long userId,
			@QueryParam("startPage") @DefaultValue("0") int startPage,
			@QueryParam("size") @DefaultValue("15") int size,
			@QueryParam("sortOrder") @DefaultValue("asc") String sortOrder) {
		List<BlogListDTO> blogListDTO;

		if (userId != null) {
			blogListDTO = blogService.getBlogsByUserId(userId, startPage, size, sortOrder);
			Log.info("Returning " + blogListDTO.size() + " blogs for user with ID " + userId);
		} else {
			blogListDTO = blogService.getBlogs(startPage, size, sortOrder);
			Log.info("Returning " + blogListDTO.size() + " blogs");
		}

		if (blogListDTO.isEmpty()) {
			throw new ObjectIsEmptyException("Return list of Blogs is Empty");
		} else {
			return Response.ok(blogListDTO).build();
		}
	}

	@GET
	@Path("/maxPage")
	@RolesAllowed({"User", "Admin"})
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMaxBlogPage(@QueryParam("size") @DefaultValue("15") int size) {
		int maxPages = blogService.getMaxBlogPage(size);
		Log.info("Returning " + maxPages + " pages");
		return Response.ok(maxPages).build();
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
	public Response fetchBlogById(@PathParam("blogId") Long id,
			@QueryParam("commentStart") @DefaultValue("0") int commentStart,
			@QueryParam("commentSize") @DefaultValue("15") int commentSize,
			@QueryParam("sortByDateAsc") @DefaultValue("true") boolean sortByDateAsc) {
		BlogDetailsDTO blogDetailsDTO = this.blogService.getBlogDetailsDTOById(id, commentStart, commentSize,
				sortByDateAsc);
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
		BlogBaseDTO updatedBlogBaseDTO = this.blogService.addUserToBlog(blogId, userId);
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
		BlogBaseDTO blogBaseDTO = new BlogBaseDTO(blogRequest.getTitle(), blogRequest.getText(),
				blogRequest.getUserId());
		BlogBaseDTO createdBlog = this.blogService.addBlog(blogBaseDTO);
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
		BlogBaseDTO blogBaseDTO = blogService.getBlogBaseDTOById(id);
		blogBaseDTO.setTitle(blogRequest.getTitle());
		blogBaseDTO.setText(blogRequest.getText());
		blogBaseDTO.setUserId(blogRequest.getUserId());
		BlogBaseDTO updatedBlogBaseDTO = blogService.updateBlog(id, blogBaseDTO);
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
		BlogDetailsDTO blogDetailsDTO = this.blogService.getBlogDetailsDTOById(id);
		this.blogService.deleteBlog(id);
		Log.info("Blog with ID: " + id + " deleted");
		return Response.ok(blogDetailsDTO).build();

	}

}
