package ch.hftm.blog.boundry;


import ch.hftm.blog.control.CommentGraphQLService;
import ch.hftm.blog.dto.requerstDTO.CommentRequest;
import ch.hftm.blog.dto.requerstDTO.PaginationParams;
import ch.hftm.blog.dto.responseDTO.PaginationResponse;
import ch.hftm.blog.entity.Comment;
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
public class CommentGraphQLResource {

    @Inject
    CommentGraphQLService commentService;

    @Query("getAllComments")
    @RolesAllowed({"User", "Admin"})
    @Description("Get all comments")
    public PaginationResponse<Comment> getComments(PaginationParams paginationParams) {
        PaginationResponse<Comment> allComments = commentService.getComments(paginationParams);
        Log.info("Returning " + allComments.getContent().size() + " comments");
        return allComments;
    }

    @Query("getCommentById")
    @RolesAllowed({"User", "Admin"})
    @Description("Get comment by ID")
    public Comment getCommentById(Long id) {
        Comment comment = commentService.getCommentById(id);
        Log.info("Returning comment with ID " + id);
        return comment;
    }

    @Mutation("createComment")
    @RolesAllowed({"User", "Admin"})
    @Description("Create comment")
    public Comment createComment(CommentRequest commentRequest) {
        Comment newComment = commentService.addComment(commentRequest);
        Log.info("Creating comment with ID " + newComment.getId());
        return newComment;
    }

    @Mutation("deleteComment")
    @RolesAllowed({"User", "Admin"})
    @Description("Delete comment")
    public void deleteComment(Long id) {
        commentService.deleteComment(id);
        Log.info("Deleting comment with ID " + id);
    }
}
