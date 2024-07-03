package ch.hftm.blog.boundry;

import java.util.List;

import ch.hftm.blog.control.BlogService;
import ch.hftm.blog.control.CommentService;
import ch.hftm.blog.control.UserService;

import ch.hftm.blog.dto.*;

import ch.hftm.blog.dto.requerstDTO.BlogRequest;
import ch.hftm.blog.exception.ObjectNotFoundException;

import io.quarkus.logging.Log;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.*;

import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;




@Path("blog") // Unter welchem Web-Pfad die Ressource erreichbar ist. Diese Annotation darfst du zusätzlich auch direkt über der Methode anbringen
@ApplicationScoped
public class BlogResource {

    @Inject
    BlogService blogService;

    @Inject
    UserService userService;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "List of blogs", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = BlogListDTO[].class)))
    // FETCH ALL BLOGS
    public Response fetchAllBlogs(@QueryParam("userId") Long userId, @QueryParam("start")@DefaultValue("0") int start, @QueryParam("size")@DefaultValue("15") int size) {
        List<BlogListDTO> blogListDTO;
        if (userId != null) {
            blogListDTO = this.blogService.getBlogsByUserId(userId, start, size);
            Log.info("Returning " + blogListDTO.size() + " blogs for user with ID " + userId);
        } else {
            blogListDTO = this.blogService.getBlogs(start, size);
            Log.info("Returning " + blogListDTO.size() + " blogs");
        }
        return Response.ok(blogListDTO).build();
    }

    @Path("/{blogId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Blog found", content = @Content(schema = @Schema(implementation = BlogDetailsDTO.class))),
            @APIResponse(responseCode = "404", description = "Blog not found")
    })
    // FETCH BLOG BY ID
    public Response fetchBlogById(@PathParam("blogId") Long id, @QueryParam("commentStart")@DefaultValue("0") int commentStart,
                                  @QueryParam("commentSize")@DefaultValue("15") int commentSize, @QueryParam("sortByDateAsc")@DefaultValue("true") boolean sortByDateAsc) {

        try {
            BlogDetailsDTO blogDetailsDTO = this.blogService.getBlogDetailsDTOById(id, commentStart, commentSize, sortByDateAsc);
            if (blogDetailsDTO == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(blogDetailsDTO).build();
        } catch (ObjectNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "201", description = "Blog created", content = @Content(schema = @Schema(implementation = BlogBaseDTO.class)))
    // CREATE NEW BLOG
    public Response createNewBlog(BlogRequest blogRequest) {
        BlogBaseDTO blogBaseDTO = new BlogBaseDTO(blogRequest.getTitle(), blogRequest.getText(), blogRequest.getUserId());
        BlogBaseDTO createdBlog = this.blogService.addBlog(blogBaseDTO);
        return Response.status(Response.Status.CREATED).entity(createdBlog).build();
    }

    @PUT
    @Path("/{blogId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Blog updated", content = @Content(schema = @Schema(implementation = BlogBaseDTO.class))),
            @APIResponse(responseCode = "404", description = "Blog not found")
    })
    //Update Blog with comments
    public Response updateBlog(@PathParam("blogId") Long id, BlogRequest blogRequest) {
        try {
            BlogBaseDTO blogBaseDTO = blogService.getBlogBaseDTOById(id);
            blogBaseDTO.setTitle(blogRequest.getTitle());
            blogBaseDTO.setText(blogRequest.getText());
            blogBaseDTO.setUserId(blogRequest.getUserId());

            BlogBaseDTO updatedBlogBaseDTO = blogService.updateBlog(id, blogBaseDTO);
            return Response.ok(updatedBlogBaseDTO).build();
        } catch (ObjectNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{blogId}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Blog deleted", content = @Content(schema = @Schema(implementation = BlogBaseDTO.class))),
            @APIResponse(responseCode = "404", description = "Blog not found")
    })
    // REMOVE BLOG
    public Response removeBlog(@PathParam("blogId") Long id) {
        try {
            BlogBaseDTO blogBaseDTO = this.blogService.getBlogBaseDTOById(id);
            this.blogService.deleteBlog(id);
            return Response.ok(blogBaseDTO).build();
        } catch (ObjectNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    //TODO Remove?
    @Path("/toUser/{blogId}/{userId}")
    @GET
    @APIResponses({
            @APIResponse(responseCode = "200", description = "User assigned to blog", content = @Content(schema = @Schema(implementation = UserBaseDTO.class))),
            @APIResponse(responseCode = "404", description = "Blog or User not found")
    })
    // USER TO BLOG
    public Response assignUserToBlog(@PathParam("blogId") Long blogId, @PathParam("userId") Long userId) {
        UserDetailsDTO userDetailsDTO = this.userService.getUserDTOById(userId);
        if (userDetailsDTO == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
        }
        try {
            BlogBaseDTO updatedBlogBaseDTO = this.blogService.addUserToBlog(blogId, userId);
            return Response.ok(updatedBlogBaseDTO).build();
        } catch (ObjectNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}