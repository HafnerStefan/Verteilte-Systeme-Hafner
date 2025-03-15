package ch.hftm.blog.boundry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ch.hftm.blog.dto.requerstDTO.*;
import ch.hftm.blog.dto.responseDTO.PaginationResponse;
import ch.hftm.blog.entity.User;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import ch.hftm.blog.control.UserService;
import ch.hftm.blog.dto.UserBaseDTO;
import ch.hftm.blog.dto.UserDetailsDTO;
import ch.hftm.blog.dto.UserListDTO;
import ch.hftm.blog.dto.mapper.UserMapper;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("users")
@ApplicationScoped

public class UserRESTResource {

    @Inject
    UserService userService;

    @GET
    @RolesAllowed({"User", "Admin"})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "List of all Users", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UserListDTO[].class)))
    // GET USERS
    public Response getUsers(PaginationParams paginationParams) {
        PaginationResponse<User> users = userService.getUsers(paginationParams);
        PaginationResponse<UserListDTO> userDTOsResponse = new PaginationResponse<>(
                users.getContent().stream().map(UserMapper::toUserListDTO).toList(),
                users.getTotalElements(),
                users.getPage(),
                users.getSize()
        );
        Log.info("Returning " + userDTOsResponse.getContent().size() + " users");
        return Response.ok(userDTOsResponse).build();
    }

    @GET
    @Path("/id:{userId}")
    @RolesAllowed({"User", "Admin"})
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "User by ID", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UserDetailsDTO.class)))
    @APIResponse(responseCode = "404", description = "User not found")
    // GET USER BY ID
    public Response getUserById(@PathParam("userId") Long id) {
        UserDetailsDTO userDetailsDTO = UserMapper.toUserDetailsDTO(userService.getUserById(id));
        Log.info("Returning User " + userDetailsDTO.getName() + " with ID " + id);
        return Response.ok(userDetailsDTO).build();

    }

    @GET
    @Path("/name:{userName}")
    @RolesAllowed({"User", "Admin"})
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "User by Name", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UserDetailsDTO.class)))
    @APIResponse(responseCode = "404", description = "User not found")
    // GET USER BY NAME
    public Response getUsersByName(@PathParam("userName") String name) {

        List<UserDetailsDTO> users = userService.getUsersByName(name).stream().map(UserMapper::toUserDetailsDTO).collect(Collectors.toList());
        for (UserDetailsDTO user : users) {
            Log.info("Returning founded User by Name " + name + " with ID " + user.getId());
        }
        return Response.ok(users).build();

    }

    @GET
    @Path("/email:{userEmail}")
    @RolesAllowed({"User", "Admin"})
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "User by Email", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UserDetailsDTO.class)))
    @APIResponse(responseCode = "404", description = "User not found")
    // GET USER BY EMAIL
    public Response fetchUserByEmail(@PathParam("userEmail") String email) {
        UserDetailsDTO userDetailsDTO = UserMapper.toUserDetailsDTO(userService.getUserByEmail(email));
        Log.info("Returning User " + userDetailsDTO.getName() + " found by Email " + email + " with ID "
                + userDetailsDTO.getId());
        return Response.ok(userDetailsDTO).build();

    }

    @POST
    @Path("/login")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest loginRequest) {
        User user = userService.authenticateUser(loginRequest);

        Map<String, Object> response = new HashMap<>();
        if (user != null) {
            // Hier werden die Rollen als Set übergeben
            String token = userService.generateJwtToken(user.getId());

            response.put("success", true);
            response.put("user", UserMapper.toUserBaseDTO(user));

            return Response.ok(response)
                    .header("Authorization", "Bearer " + token)
                    .build();
        } else {
            response.put("success", false);
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(response)
                    .build();
        }
    }

    @POST
    @Path("/validate-token")
    @PermitAll
    public Response validateToken(@HeaderParam("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        String token = authorizationHeader.substring("Bearer".length()).trim();
        Map<String, Object> response = new HashMap<>();
        try {
            UserBaseDTO user = UserMapper.toUserBaseDTO(userService.validateJwtToken(token));
            response.put("success", true);
            response.put("user", user);
            return Response.ok(response).build(); // Token ist gültig
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build(); // Token ist ungültig oder abgelaufen
        }
    }

    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "201", description = "User created", content = @Content(schema = @Schema(implementation = UserBaseDTO.class)))
    // ADD USES
    public Response createNewUser(
            @Valid @jakarta.validation.groups.ConvertGroup(from = Default.class, to = ValidationGroups.Create.class) UserCreateRequest userCreateRequest) {
        Log.info("Received UserRequest: name=" + userCreateRequest.getName() + ", email="
                + userCreateRequest.getEmail());

        UserBaseDTO createdUser= UserMapper.toUserBaseDTO(userService.addUser(userCreateRequest));

        Log.info("Adding User " + createdUser.getName() + " with ID " + createdUser.getId());
        return Response.status(Response.Status.CREATED).entity(createdUser).build();
    }

    @PUT
    @Path("/{userId}")
    @RolesAllowed({"Admin"})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "User updated", content = @Content(schema = @Schema(implementation = UserBaseDTO.class)))
    @APIResponse(responseCode = "404", description = "User not found")
    // UPDATE USER
    public Response updateUser(@PathParam("userId") Long id,
            @Valid @jakarta.validation.groups.ConvertGroup(from = Default.class, to = ValidationGroups.Update.class) UserRequest userRequest) {
        UserBaseDTO updateUser = UserMapper.toUserBaseDTO(userService.updateUser(userRequest));
        Log.info("Updating User " + userRequest.getName() + " with ID " + id);
        return Response.ok(updateUser).build();
    }

    @PUT
    @Path("/{id}/change-password")
    @RolesAllowed({"Admin"})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response changePassword(@PathParam("id") Long id, PasswordChangeRequest passwordChangeRequest) {
        userService.changePassword(id, passwordChangeRequest);
        UserBaseDTO updateUser = UserMapper.toUserBaseDTO(userService.getUserById(id));
        Log.info("Changing password for User " + updateUser.getName() + " with ID " + id);
        return Response.ok(updateUser).build();

    }

    @DELETE
    @RolesAllowed({"Admin"})
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses({
            @APIResponse(responseCode = "200", description = "User deleted", content = @Content(schema = @Schema(implementation = UserDetailsDTO.class))),
            @APIResponse(responseCode = "404", description = "User not found")
    })
    // REMOVE USER
    public Response removeUser(@PathParam("userId") Long id) {
        UserDetailsDTO userDetailsDTO = UserMapper.toUserDetailsDTO(userService.getUserById(id));;
        this.userService.deleteUser(id);
        Log.info("Deleting User " + userDetailsDTO.getName() + " with ID " + id);
        return Response.ok(userDetailsDTO).build();

    }
}
