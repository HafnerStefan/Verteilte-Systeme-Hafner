package ch.hftm.blog.boundry;

import java.util.List;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import ch.hftm.blog.dto.CommentBaseDTO;
import ch.hftm.blog.dto.requerstDTO.CommentRequest;

import ch.hftm.blog.control.CommentService;

import ch.hftm.blog.dto.CommentWithBlogContextDTO;
import ch.hftm.blog.dto.CommentWithBlogTitleDTO;

import io.quarkus.logging.Log;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("comment")
@ApplicationScoped
public class CommentResource {
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
	@Path("/commentId:{commentId}")
	@RolesAllowed({"Admin"})
	@Produces(MediaType.APPLICATION_JSON)
	@APIResponse(responseCode = "200", description = "Comment by ID", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = CommentBaseDTO.class)))
	@APIResponse(responseCode = "404", description = "Comment not found")
	//FETCH Comment BY ID
	public Response fetchCommentById(@PathParam("commentId") String id) {
		ObjectId commentId = new ObjectId(id);
		CommentBaseDTO commentBaseDTO = this.commentService.getCommentDTOById(commentId);
		Log.info("Returning comment with ID: " + id);
		return Response.ok(commentBaseDTO).build();
	}


	@GET
	@Path("/maxPage")
	@RolesAllowed({"User", "Admin"})
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCommentPageByBlogId(@QueryParam("blogId") String id, @QueryParam("size") @DefaultValue("6") int size) {
		ObjectId blogId = new ObjectId(id);
		int maxPages = commentService.getMaxCommentPageByBlogId(blogId,size);
		Log.info("Returning " + maxPages + " pages for blog with ID " + blogId);
		return Response.ok(maxPages).build();
	}

	//TODO Remove ?
	@GET
	@Path("/blogId:{blogId}")
	@RolesAllowed({"User", "Admin"})
	@Produces(MediaType.APPLICATION_JSON)
	@APIResponse(responseCode = "200", description = "Comments by Blog ID", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = CommentBaseDTO[].class)))
	@APIResponse(responseCode = "404", description = "Comments not found")
	public Response getCommentsByBlogId(@PathParam("blogId") String id) {
		ObjectId blogId = new ObjectId(id);
		List<CommentBaseDTO> comments = commentService.getCommentsByBlogId(blogId);
		Log.info("Returning " + comments.size() + " comments for blog with ID " + blogId);
		return Response.ok(comments).build();
	}

	@GET
	@Path("/context:{commentId}")
	@RolesAllowed({"Admin"})
	@Produces(MediaType.APPLICATION_JSON)
	@APIResponse(responseCode = "200", description = "Comment with context by ID", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = CommentWithBlogContextDTO.class)))
	@APIResponse(responseCode = "404", description = "Comment not found")
	public Response getCommentWithContextById(@PathParam("commentId") String id,
			@QueryParam("previousCommentSize") @DefaultValue("2") int previousCommentSize,
			@QueryParam("nextCommentSize") @DefaultValue("4") int nextCommentSize) {
		ObjectId commentId = new ObjectId(id);
		CommentWithBlogContextDTO commentContext = this.commentService.getCommentWithBlogContextById(commentId,
				previousCommentSize, nextCommentSize);
		Log.info("Returning comment with context and ID: " + commentId);
		return Response.ok(commentContext).build();
	}

	@GET
	@Path("/byuser/{userId}")
	@RolesAllowed({"User", "Admin"})
	@Produces(MediaType.APPLICATION_JSON)
	@APIResponse(responseCode = "200", description = "Comments by User ID", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = CommentWithBlogTitleDTO[].class)))
	@APIResponse(responseCode = "404", description = "Comments not found")
	public Response getCommentsByUserId(@PathParam("userId") String id) {
		ObjectId userId = new ObjectId(id);
		List<CommentWithBlogTitleDTO> comments = commentService.getCommentsWithBlogTitleByUserId(userId);
		Log.info("Returning " + comments.size() + " comments for user with ID " + userId);
		return Response.ok(comments).build();
	}

	@POST
	@RolesAllowed({"User", "Admin"})
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@APIResponse(responseCode = "201", description = "Comment created", content = @Content(schema = @Schema(implementation = CommentBaseDTO.class)))
	// CREATE NEW COMMENT
	public Response createNewComment(CommentRequest commentRequest) {
		CommentBaseDTO commentBaseDTO = new CommentBaseDTO(commentRequest.getText(), commentRequest.getBlogId(),
				commentRequest.getUserId());
		CommentBaseDTO createdComment = this.commentService.addComment(commentBaseDTO);
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
	public Response removeComment(@PathParam("commentId") String id) {
		ObjectId commentId = new ObjectId(id);
		CommentBaseDTO commentBaseDTO = this.commentService.getCommentDTOById(commentId);
		commentService.deleteComment(commentId);
		Log.info("Comment with ID: " + id + " deleted");
		return Response.ok(commentBaseDTO).build();
	}

}
