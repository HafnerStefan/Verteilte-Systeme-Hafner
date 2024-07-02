package ch.hftm.blog.boundry;

import java.util.List;

import io.quarkus.logging.Log;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import ch.hftm.blog.control.BlogService;
import ch.hftm.blog.control.CommentService;
import ch.hftm.blog.control.UserService;
import ch.hftm.blog.dto.BlogDTO;
import ch.hftm.blog.dto.UserDTO;
import ch.hftm.blog.exception.ObjectNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("blog") // Unter welchem Web-Pfad die Ressource erreichbar ist. Diese Annotation darfst du zusätzlich auch direkt über der Methode anbringen
@ApplicationScoped
public class BlogResource {

    @Inject
    BlogService blogService;

    @Inject
    UserService userService;

    @Inject
    CommentService commentService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "List of blogs", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = BlogDTO[].class)))
    //FETCH ALL BLOGS
    public Response fetchAllBlogs() {
        List<BlogDTO> blogsDTO = this.blogService.getBlogs();
        Log.info("Returning " + blogsDTO.size() + " blogs");
        return Response.ok(blogsDTO).build();
    }

    @Path("/{blogId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Blog found", content = @Content(schema = @Schema(implementation = BlogDTO.class))),
            @APIResponse(responseCode = "404", description = "Blog not found")
    })
    //FETCH BLOG BY ID
    public Response fetchBlogById(@PathParam("blogId") Long id) {

        try {
            BlogDTO blogDTO = this.blogService.getBlogDTOById(id);
            if (blogDTO == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(blogDTO).build();
        } catch (ObjectNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "201", description = "Blog created", content = @Content(schema = @Schema(implementation = BlogDTO.class)))
    // CREATE NEW BLOG
    public Response createNewBlog(BlogRequest blogRequest) {
        BlogDTO blogDTO = new BlogDTO(blogRequest.getTitle(), blogRequest.getText(), blogRequest.getUserId());
        BlogDTO createdBlog = this.blogService.addBlog(blogDTO);
        return Response.status(Response.Status.CREATED).entity(createdBlog).build();
    }

    @PUT
    @Path("/{blogId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Blog updated", content = @Content(schema = @Schema(implementation = BlogDTO.class))),
            @APIResponse(responseCode = "404", description = "Blog not found")
    })
    //Update Blog with comments
    public Response updateBlog(@PathParam("blogId") Long id, BlogRequest blogRequest) {
        try {
            BlogDTO blogDTO = blogService.getBlogDTOById(id);
            blogDTO.setTitle(blogRequest.getTitle());
            blogDTO.setText(blogRequest.getText());
            blogDTO.setUserId(blogRequest.getUserId());

            // Update comments if IDs have been passed
            if (blogRequest.getCommentIds() != null) {
                List<Long> commentsIds = blogRequest.getCommentIds();
                blogDTO.setCommentsIds(commentsIds);
            }

            BlogDTO updatedBlogDTO = blogService.updateBlog(id, blogDTO);
            return Response.ok(updatedBlogDTO).build();
        } catch (ObjectNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{blogId}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Blog deleted", content = @Content(schema = @Schema(implementation = BlogDTO.class))),
            @APIResponse(responseCode = "404", description = "Blog not found")
    })
    // REMOVE BLOG
    public Response removeBlog(@PathParam("blogId") Long id) {
        try {
            BlogDTO blogDTO = this.blogService.getBlogDTOById(id);
            this.blogService.deleteBlog(id);
            return Response.ok(blogDTO).build();
        } catch (ObjectNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    //TODO Remove?
    @Path("/toUser/{blogId}/{userId}")
    @GET
    @APIResponses({
            @APIResponse(responseCode = "200", description = "User assigned to blog", content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @APIResponse(responseCode = "404", description = "Blog or User not found")
    })
    // USER TO BLOG
    public Response assignUserToBlog(@PathParam("blogId") Long blogId, @PathParam("userId") Long userId) {
        UserDTO userDTO = this.userService.getUserDTOById(userId);
        if (userDTO == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
        }
        try {
            BlogDTO updatedBlogDTO = this.blogService.addUserToBlog(blogId, userId);
            return Response.ok(updatedBlogDTO).build();
        } catch (ObjectNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}