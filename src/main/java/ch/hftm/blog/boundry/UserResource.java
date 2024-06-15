package ch.hftm.blog.boundry;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import ch.hftm.blog.control.UserService;
import ch.hftm.blog.entity.User;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("user") // Unter welchem Web-Pfad die Ressource erreichbar ist. Diese Annotation darfst du zusätzlich auch direkt über der Methode anbringen
@ApplicationScoped

public class UserResource {

    @Inject
    UserService userService;

    @GET // Diese Methode ist über eine http-GET-Anfrage erreichbar.
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "List of all Users", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = User[].class)))
    public Response getUsers() {
        List<User> users = this.userService.getUsers();
        Log.info("Returning " + users.size() + " users");
        return Response.ok(users).build();
    }

    @POST // Diese Methode ist über eine http-POST-Anfrage erreichbar.
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "201", description = "User created", content = @Content(schema = @Schema(implementation = User.class)))
    public Response addUser(UserRequest userRequest) {
        Log.info("Received UserRequest: name=" + userRequest.getName() + ", age=" + userRequest.getAge());
        String name = userRequest.getName();
        int age = userRequest.getAge();
        User user = new User(name, age);
        this.userService.addUser(user);
        Log.info("Adding User " + user.getName());
        return Response.status(Response.Status.CREATED).entity(user).build();

    }

}
