package ch.hftm.blog.boundry;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import ch.hftm.blog.control.BlogService;
import ch.hftm.blog.control.CommentService;
import ch.hftm.blog.control.UserService;
import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.Comment;
import ch.hftm.blog.entity.User;
import ch.hftm.blog.exception.ObjectNotFoundException;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("comments")
@ApplicationScoped
public class CommentResource {

    @Inject
    CommentService commentService;

    @Inject
    UserService userService;

    @Inject
    BlogService blogService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "List of all Comments", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Comment[].class)))
    public Response getComments() {
        List<Comment> comments = commentService.getComments();
        Log.info("Returning " + comments.size() + " comments");
        return Response.ok(comments).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "Comment by ID", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Comment.class)))
    @APIResponse(responseCode = "404", description = "Comment not found")
    public Response getCommentById(@PathParam("id") Long id) {
        try {
            Comment comment = commentService.getCommentById(id);
            return Response.ok(comment).build();
        } catch (ObjectNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/byblog/{blogId}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "Comments by Blog ID", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Comment[].class)))
    @APIResponse(responseCode = "404", description = "Comments not found")
    public Response getCommentsByBlogId(@PathParam("blogId") Long blogId) {
        List<Comment> comments = commentService.getCommentsByBlogId(blogId);
        if (comments.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No comments found for blog with ID " + blogId)
                    .build();
        } else {
            return Response.ok(comments).build();
        }
    }

    @GET
    @Path("/byuser/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "Comments by User ID", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Comment[].class)))
    @APIResponse(responseCode = "404", description = "Comments not found")
    public Response getCommentsByUserId(@PathParam("userId") Long userId) {
        List<Comment> comments = commentService.getCommentsByUserId(userId);
        if (comments.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No comments found for user with ID " + userId)
                    .build();
        } else {
            return Response.ok(comments).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "201", description = "Comment created", content = @Content(schema = @Schema(implementation = Comment.class)))
    public Response addComment(CommentRequest commentRequest) {
        try {
            String text = commentRequest.getText();
            User user = userService.getUserById(commentRequest.getUserId());
            Blog blog = blogService.getBlogById(commentRequest.getBlogId());
            Comment comment = new Comment(text, user, blog);
            commentService.addComment(comment);
            return Response.status(Response.Status.CREATED).entity(comment).build();
        } catch (ObjectNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Comment updated", content = @Content(schema = @Schema(implementation = Comment.class))),
            @APIResponse(responseCode = "404", description = "Comment not found")
    })
    public Response updateComment(@PathParam("id") Long id, CommentRequest commentRequest) {
        try {
            Comment commentDetails = new Comment(commentRequest.getText(),
                    userService.getUserById(commentRequest.getUserId()),
                    blogService.getBlogById(commentRequest.getBlogId()));
            commentService.updateComment(id, commentDetails);
            return Response.ok(commentDetails).build();
        } catch (ObjectNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Comment deleted"),
            @APIResponse(responseCode = "404", description = "Comment not found")
    })
    public Response deleteComment(@PathParam("id") Long id) {
        try {
            commentService.deleteComment(id);
            return Response.ok().build();
        } catch (ObjectNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

}
