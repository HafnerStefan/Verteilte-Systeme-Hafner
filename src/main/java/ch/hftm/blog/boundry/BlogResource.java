package ch.hftm.blog.boundry;

import java.util.List;
import java.util.stream.Collectors;

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
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
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

    @GET // Diese Methode ist über eine http-GET-Anfrage erreichbar.
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "List of blogs", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Blog[].class)))
    //FETCH ALL BLOGS
    public Response fetchAllBlogs() {
        List<Blog> blogs = this.blogService.getBlogs();
        return Response.ok(blogs).build();
    }

    @Path("/byId")
    @GET // Diese Methode ist über eine http-GET-Anfrage erreichbar.
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Blog found", content = @Content(schema = @Schema(implementation = Blog.class))),
            @APIResponse(responseCode = "404", description = "Blog not found")
    })
    //FETCH BLOG BY ID
    public Response fetchBlogById(@QueryParam("blogId") Long id) {

        try {
            Blog blog = this.blogService.getBlogById(id);
            if (blog == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(blog).build();
        } catch (ObjectNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST // Diese Methode ist über eine http-POST-Anfrage erreichbar.
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "201", description = "Blog created", content = @Content(schema = @Schema(implementation = Blog.class)))
    // CREATE NEW BLOG
    public Response createNewBlog(
            BlogRequest blogRequest) {
        String title = blogRequest.getTitle();
        String text = blogRequest.getText();
        User user = userService.getUserById(blogRequest.getUserId());
        Blog blog = new Blog(title, text, user);
        this.blogService.addBlog(blog);
        return Response.status(Response.Status.CREATED).entity(blog).build();
    }

    @PUT
    @Path("/byId")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Blog updated", content = @Content(schema = @Schema(implementation = Blog.class))),
            @APIResponse(responseCode = "404", description = "Blog not found")
    })
    //Update Blog with comments
    public Response updateBlog(@QueryParam("blogId") Long id, BlogRequest blogRequest) {
        try {
            Blog blog = blogService.getBlogById(id);
            blog.setTitle(blogRequest.getTitle());
            blog.setText(blogRequest.getText());
            blog.setUser(userService.getUserById(blogRequest.getUserId()));

            // Aktualisiere Kommentare, falls IDs übergeben wurden
            if (blogRequest.getCommentIds() != null) {
                List<Comment> comments = blogRequest.getCommentIds().stream()
                        .map(commentService::getCommentById)
                        .collect(Collectors.toList());
                blog.setComments(comments);
            }

            blogService.updateBlog(id, blog);
            return Response.ok(blog).build();
        } catch (ObjectNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/byId")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Blog deleted", content = @Content(schema = @Schema(implementation = Blog.class))),
            @APIResponse(responseCode = "404", description = "Blog not found")
    })
    // REMOVE BLOG
    public Response removeBlog(@QueryParam("blogId") Long id) {
        try {
            Blog blog = this.blogService.getBlogById(id);
            this.blogService.deleteBlog(id);
            return Response.ok(blog).build();
        } catch (ObjectNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @Path("/toUser")
    @GET // Diese Methode ist über eine http-POST-Anfrage erreichbar.
    @APIResponses({
            @APIResponse(responseCode = "200", description = "User assigned to blog", content = @Content(schema = @Schema(implementation = User.class))),
            @APIResponse(responseCode = "404", description = "Blog or User not found")
    })
    // USER TO BLOG
    public Response assignUserToBlog(@QueryParam("blogId") Long blogId, @QueryParam("userid") Long userId) {
        User user = this.userService.getUserById(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
        }
        try {
            this.blogService.addUserToBlog(blogId, user);
            return Response.ok(user).build();
        } catch (ObjectNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}