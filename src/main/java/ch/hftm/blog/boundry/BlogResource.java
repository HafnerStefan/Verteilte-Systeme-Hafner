package ch.hftm.blog.boundry;

import java.util.List;

import ch.hftm.blog.control.BlogService;
import ch.hftm.blog.control.UserService;
import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;

@Path("blog") // Unter welchem Web-Pfad die Ressource erreichbar ist. Diese Annotation darfst du zusätzlich auch direkt über der Methode anbringen
@ApplicationScoped
public class BlogResource {

    @Inject
    BlogService blogService;

    @Inject
    UserService userService;

    @GET // Diese Methode ist über eine http-GET-Anfrage erreichbar.
    public List<Blog> getEntries() {
        return this.blogService.getBlogs();
    }

    @Path("/{id}")

    @GET // Diese Methode ist über eine http-GET-Anfrage erreichbar.
    public Blog getEntryById(@PathParam("id") Long id) {
        return this.blogService.getBlogById(id);
    }

    @DELETE
    @Path("/{id}")
    public void deleteEntry(@PathParam("id") Long id) {
        this.blogService.deleteBlog(id);
    }

    @POST // Diese Methode ist über eine http-POST-Anfrage erreichbar.
    @Consumes(MediaType.APPLICATION_JSON)
    public void addBlog(BlogRequest blogRequest) {
        String title = blogRequest.getTitle();
        String text = blogRequest.getText();
        this.blogService.addBlog(title, text);
    }

    @Path("/{blogid}/{userid}")
    @POST // Diese Methode ist über eine http-POST-Anfrage erreichbar.
    public void addUserToBlog(@PathParam("blogid") Long blogId, @PathParam("userid") Long userId) {
        User user = this.userService.getUserById(userId);
        this.blogService.addUserToBlog(blogId, user);
    }

}