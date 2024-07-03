package ch.hftm.blog.boundry;

import java.util.List;



import jakarta.ws.rs.*;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;


import ch.hftm.blog.dto.CommentBaseDTO;
import ch.hftm.blog.dto.requerstDTO.CommentRequest;

import ch.hftm.blog.control.CommentService;

import ch.hftm.blog.exception.ObjectNotFoundException;
import ch.hftm.blog.dto.CommentWithBlogContextDTO;
import ch.hftm.blog.dto.CommentWithBlogTitleDTO;


import org.jboss.logging.Logger; // Importiere den Logger

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("comment")
@ApplicationScoped
public class CommentResource {

	private static final Logger Log = Logger.getLogger(CommentResource.class);

	@Inject
	CommentService commentService;


//TODO REMOVE?
/*	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@APIResponse(responseCode = "200", description = "List of all Comments", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = CommentDTO[].class)))
	//FETCH ALL Comments
	public Response fetchAllComments() {
		List<CommentDTO> commentsDTO = commentService.getComments();
		Log.info("Returning " + commentsDTO.size() + " comments");
		return Response.ok(commentsDTO).build();
	}*/

	@GET
	@Path("/{commentId}")
	@Produces(MediaType.APPLICATION_JSON)
	@APIResponse(responseCode = "200", description = "Comment by ID", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = CommentBaseDTO.class)))
	@APIResponse(responseCode = "404", description = "Comment not found")
	//FETCH Comment BY ID
	public Response fetchCommentById(@PathParam("commentId") Long id) {
		try {
			CommentBaseDTO commentBaseDTO = this.commentService.getCommentDTOById(id);
			if (commentBaseDTO == null) {
				return Response.status(Response.Status.NOT_FOUND).build();
			}
			return Response.ok(commentBaseDTO).build();
		} catch (ObjectNotFoundException e) {
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@APIResponse(responseCode = "201", description = "Comment created", content = @Content(schema = @Schema(implementation = CommentBaseDTO.class)))
	// CREATE NEW COMMENT
	public Response createNewComment(CommentRequest commentRequest) {
		CommentBaseDTO commentBaseDTO = new CommentBaseDTO(commentRequest.getText(), commentRequest.getBlogId(), commentRequest.getUserId());
		CommentBaseDTO createdComment = this.commentService.addComment(commentBaseDTO);
		return Response.status(Response.Status.CREATED).entity(createdComment).build();

	}

//TODO Remove ?
@GET
@Path("/{blogId}")
@Produces(MediaType.APPLICATION_JSON)
@APIResponse(responseCode = "200", description = "Comments by Blog ID", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = CommentBaseDTO[].class)))
@APIResponse(responseCode = "404", description = "Comments not found")
public Response getCommentsByBlogId(@PathParam("blogId") Long blogId) {
	List<CommentBaseDTO> comments = commentService.getCommentsByBlogId(blogId);
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
	@APIResponse(responseCode = "200", description = "Comments by User ID", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = CommentWithBlogTitleDTO[].class)))
	@APIResponse(responseCode = "404", description = "Comments not found")
	public Response getCommentsByUserId(@PathParam("userId") Long userId) {
		List<CommentWithBlogTitleDTO> comments = commentService.getCommentsWithBlogTitleByUserId(userId);
		if (comments.isEmpty()) {
			return Response.status(Response.Status.NOT_FOUND).entity("No comments found for user with ID " + userId)
					.build();
		} else {
			return Response.ok(comments).build();
		}
	}

	@GET
	@Path("/context/{commentId}")
	@Produces(MediaType.APPLICATION_JSON)
	@APIResponse(responseCode = "200", description = "Comment with context by ID", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = CommentWithBlogContextDTO.class)))
	@APIResponse(responseCode = "404", description = "Comment not found")
	public Response getCommentWithContextById(@PathParam("commentId") Long id, @QueryParam("previousCommentSize")@DefaultValue("2") int previousCommentSize,
											  @QueryParam("nextCommentSize")@DefaultValue("4") int nextCommentSize) {
		try {
			CommentWithBlogContextDTO commentContext = this.commentService.getCommentWithBlogContextById(id,previousCommentSize,nextCommentSize);
			return Response.ok(commentContext).build();
		} catch (ObjectNotFoundException e) {
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}




	@DELETE
	@Path("/{commentId}")
	@Produces(MediaType.APPLICATION_JSON)
	@APIResponses({
			@APIResponse(responseCode = "200", description = "Comment deleted",content = @Content(schema = @Schema(implementation = CommentBaseDTO.class))),
			@APIResponse(responseCode = "404", description = "Comment not found")
	})
	//REMOVE COMMENT
	public Response removeComment(@PathParam("commentId") Long id) {
		try {
			CommentBaseDTO commentBaseDTO = this.commentService.getCommentDTOById(id);
			commentService.deleteComment(id);
			return Response.ok(commentBaseDTO).build();
		} catch (ObjectNotFoundException e) {
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}

}
