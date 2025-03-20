package ch.hftm.blog.boundry;

import ch.hftm.blog.dto.mapper.CommentMapper;
import ch.hftm.blog.dto.requerstDTO.PaginationParams;
import ch.hftm.blog.dto.responseDTO.PaginationResponse;
import ch.hftm.blog.entity.Comment;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import ch.hftm.blog.dto.CommentBaseDTO;
import ch.hftm.blog.dto.requerstDTO.CommentRequest;

import ch.hftm.blog.control.CommentService;

import io.quarkus.logging.Log;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("comment")
@ApplicationScoped
public class CommentRESTResource {

    @Inject
    CommentService commentService;

    @GET
    @Path("/blogId/{blogId}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "List of all Comments", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = CommentBaseDTO[].class)))
    // GET COMMENTS
    public Response getComments(@PathParam("blogId") Long blogId,@BeanParam PaginationParams paginationParams) {
        PaginationResponse<Comment> comments = commentService.getComments(blogId, paginationParams);
        PaginationResponse<CommentBaseDTO> commentDTOsResponse = new PaginationResponse<>(
                comments.getContent().stream().map(CommentMapper::toCommentBaseDTO).toList(),
                comments.getTotalElements(),
                comments.getPage(),
                comments.getSize()
        );
        Log.info("Returning " + commentDTOsResponse.getContent().size() + " users");
        return Response.ok(commentDTOsResponse).build();
    }

    @GET
    @Path("/{commentId}")
    @RolesAllowed({"User","Admin"})
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "Comment by ID", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = CommentBaseDTO.class)))
    @APIResponse(responseCode = "404", description = "Comment not found")
    //FETCH Comment BY ID
    public Response fetchCommentById(@PathParam("commentId") Long id) {
        CommentBaseDTO commentBaseDTO = CommentMapper.toCommentBaseDTO(commentService.getCommentById(id));
        Log.info("Returning comment with ID: " + id);
        return Response.ok(commentBaseDTO).build();
    }

    @POST
    @RolesAllowed({"User", "Admin"})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "201", description = "Comment created", content = @Content(schema = @Schema(implementation = CommentBaseDTO.class)))
    // CREATE NEW COMMENT
    public Response createNewComment(CommentRequest commentRequest) {
        CommentBaseDTO createdComment = CommentMapper.toCommentBaseDTO(commentService.addComment(commentRequest));
        Log.info("Comment created with ID: " + createdComment.getId());
        return Response.status(Response.Status.CREATED).entity(createdComment).build();

    }

    @DELETE
    @RolesAllowed({"User", "Admin"})
    @Path("/{commentId}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Comment deleted", content = @Content(schema = @Schema(implementation = CommentBaseDTO.class))),
            @APIResponse(responseCode = "404", description = "Comment not found")
    })
    //REMOVE COMMENT
    public Response removeComment(@PathParam("commentId") Long id) {
        CommentBaseDTO commentBaseDTO = CommentMapper.toCommentBaseDTO(commentService.getCommentById(id));
        commentService.deleteComment(id);
        Log.info("Comment with ID: " + id + " deleted");
        return Response.ok(commentBaseDTO).build();
    }

}
