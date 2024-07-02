package ch.hftm.blog.boundry;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.ws.rs.*;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import ch.hftm.blog.control.UserService;
import ch.hftm.blog.dto.UserDTO;
import ch.hftm.blog.exception.ObjectNotFoundException;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("users")
@ApplicationScoped

public class UserResource {

    @Inject
    UserService userService;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "List of all Users", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UserDTO[].class)))
    // GET USERS
    public Response fetchAllUsers() {
        List<UserDTO> users = this.userService.getUsers();
        Log.info("Returning " + users.size() + " users");
        return Response.ok(users).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "201", description = "User created", content = @Content(schema = @Schema(implementation = UserDTO.class)))
    // ADD USES
    public Response addUser(
            @Valid @jakarta.validation.groups.ConvertGroup(from = Default.class, to = ValidationGroups.Create.class) UserRequest userRequest) {
        Log.info("Received UserRequest: name=" + userRequest.getName() + ", email=" + userRequest.getEmail());

        UserDTO userDTO = new UserDTO();
        userDTO.setName(userRequest.getName());
        userDTO.setAge(userRequest.getAge());
        userDTO.setEmail(userRequest.getEmail());
        userDTO.setPassword(userRequest.getPassword());
        userDTO.setAddress(userRequest.getAddress());
        userDTO.setPhone(userRequest.getPhone());
        userDTO.setGender(userRequest.getGender());
        userDTO.setDateOfBirth(userRequest.getDateOfBirth());

        UserDTO createdUser = this.userService.addUser(userDTO);
        Log.info("Adding User " + createdUser.getName());
        return Response.status(Response.Status.CREATED).entity(createdUser).build();
    }

    @PUT
    @Path("/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "User updated", content = @Content(schema = @Schema(implementation = UserDTO.class)))
    @APIResponse(responseCode = "404", description = "User not found")
    // UPDATE USER
    public Response updateUser(
            @PathParam("userId") Long id,
            @Valid @jakarta.validation.groups.ConvertGroup(from = Default.class, to = ValidationGroups.Update.class) UserRequest userRequest) {
        try {
            UserDTO userDTO = new UserDTO(id, userRequest.getName(), userRequest.getAge(), userRequest.getEmail(),
                    userRequest.getAddress(), userRequest.getPhone(),
                    userRequest.getGender(), userRequest.getDateOfBirth());
            userDTO.setUpdatedAt(LocalDateTime.now()); // Update the updatedAt field
            this.userService.updateUser(id, userDTO);

            Log.info("Updating User " + userDTO.getName());

            return Response.ok(userDTO).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses({
            @APIResponse(responseCode = "200", description = "User deleted", content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @APIResponse(responseCode = "404", description = "User not found")
    })
    // REMOVE USER
    public Response removeUser(@PathParam("userId") Long id) {
        try {
            UserDTO userDTO = this.userService.getUserDTOById(id);
            this.userService.deleteUser(id);
            return Response.ok(userDTO).build();
        } catch (ObjectNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "User by ID", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UserDTO.class)))
    @APIResponse(responseCode = "404", description = "User not found")
    // GET USER BY ID
    public Response fetchUserById(@PathParam("userId") Long id) {
        try {
            UserDTO userDTO = this.userService.getUserDTOById(id);
            return Response.ok(userDTO).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{userName}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "User by Name", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UserDTO.class)))
    @APIResponse(responseCode = "404", description = "User not found")
    // GET USER BY NAME
    public Response fetchUsersByName(@PathParam("userName") String name) {
        try {
            List<UserDTO> users = this.userService.getUsersByName(name);
            return Response.ok(users).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{userEmail}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "User by Email", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UserDTO.class)))
    @APIResponse(responseCode = "404", description = "User not found")
    // GET USER BY EMAIL
    public Response fetchUserByEmail(@PathParam("userEmail") String email) {
        try {
            UserDTO userDTO = this.userService.getUserByEmail(email);
            return Response.ok(userDTO).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

}
