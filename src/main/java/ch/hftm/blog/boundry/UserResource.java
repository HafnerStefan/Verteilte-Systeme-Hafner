package ch.hftm.blog.boundry;

import java.time.LocalDateTime;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import ch.hftm.blog.control.UserService;
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
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
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
    //GET USERS
    public Response fetchAllUsers() {
        List<User> users = this.userService.getUsers();
        Log.info("Returning " + users.size() + " users");
        return Response.ok(users).build();
    }

    @POST // Diese Methode ist über eine HTTP-POST-Anfrage erreichbar.
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "201", description = "User created", content = @Content(schema = @Schema(implementation = User.class)))
    // ADD USER
    public Response addUser(UserRequest userRequest) {
        Log.info("Received UserRequest: name=" + userRequest.getName() + ", age=" + userRequest.getAge());

        User user = new User(userRequest.getName(), userRequest.getAge(), userRequest.getEmail(),
                userRequest.getPassword(), userRequest.getAddress(), userRequest.getPhone(), userRequest.getGender(),
                userRequest.getDateOfBirth());

        this.userService.addUser(user);
        Log.info("Adding User " + user.getName());
        return Response.status(Response.Status.CREATED).entity(user).build();
    }

    @PUT
    @Path("/updateUser") // Diese Methode ist über eine HTTP-PUT-Anfrage erreichbar und verwendet einen Pfadparameter.
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "User updated", content = @Content(schema = @Schema(implementation = User.class)))
    @APIResponse(responseCode = "404", description = "User not found")
    // UPDATE USER
    public Response updateUser(@QueryParam("userId") Long id, UserRequest userRequest) {
        try {
            User user = new User(userRequest.getName(), userRequest.getAge(), userRequest.getEmail(),
                    userRequest.getPassword(), userRequest.getAddress(), userRequest.getPhone(),
                    userRequest.getGender(), userRequest.getDateOfBirth());
            user.setUpdatedAt(LocalDateTime.now()); // Aktualisiere das updatedAt-Feld
            this.userService.updateUser(id, user);

            Log.info("Updating User " + user.getName());

            return Response.ok(user).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/byId")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses({
            @APIResponse(responseCode = "200", description = "User deleted", content = @Content(schema = @Schema(implementation = User.class))),
            @APIResponse(responseCode = "404", description = "User not found")
    })
    // REMOVE USER
    public Response removeUser(@QueryParam("userId") Long id) {
        try {
            User user = this.userService.getUserById(id);
            this.userService.deleteUser(id);
            return Response.ok(user).build();
        } catch (ObjectNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/byId") // Diese Methode ist über eine HTTP-GET-Anfrage erreichbar und verwendet einen Pfadparameter.
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "User by ID", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = User.class)))
    @APIResponse(responseCode = "404", description = "User not found")
    // GET USER BY ID
    public Response fetchUserById(@QueryParam("userId") Long id) {
        try {
            User user = this.userService.getUserById(id);
            return Response.ok(user).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/byname") // Diese Methode ist über eine HTTP-GET-Anfrage erreichbar und verwendet einen Pfadparameter.
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "User by Name", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = User.class)))
    @APIResponse(responseCode = "404", description = "User not found")
    // GET USER BY NAME
    public Response fetchUserByName(@QueryParam("userName") String name) {
        try {
            User user = this.userService.getUserByName(name);
            return Response.ok(user).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/byemail") // Diese Methode ist über eine HTTP-GET-Anfrage erreichbar und verwendet einen Pfadparameter.
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "User by Email", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = User.class)))
    @APIResponse(responseCode = "404", description = "User not found")
    // GET USER BY EMAIL
    public Response fetchUserByEmail(@QueryParam("userEmail") String email) {
        try {
            User user = this.userService.getUserByEmail(email);
            return Response.ok(user).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

}
