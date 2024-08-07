package ch.hftm.blog.boundry;

import java.time.LocalDateTime;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import ch.hftm.blog.control.UserService;
import ch.hftm.blog.dto.UserBaseDTO;
import ch.hftm.blog.dto.UserDetailsDTO;
import ch.hftm.blog.dto.UserListDTO;
import ch.hftm.blog.dto.requerstDTO.LoginRequest;
import ch.hftm.blog.dto.requerstDTO.PasswordChangeRequest;
import ch.hftm.blog.dto.requerstDTO.UserCreateRequest;
import ch.hftm.blog.dto.requerstDTO.UserRequest;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
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
        @APIResponse(responseCode = "200", description = "List of all Users", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UserListDTO[].class)))
        // GET USERS
        public Response fetchAllUsers() {
                List<UserListDTO> users = this.userService.getUsers();
                Log.info("Returning " + users.size() + " users");
                return Response.ok(users).build();
        }

        @GET
        @Path("/id:{userId}")
        @Produces(MediaType.APPLICATION_JSON)
        @APIResponse(responseCode = "200", description = "User by ID", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UserDetailsDTO.class)))
        @APIResponse(responseCode = "404", description = "User not found")
        // GET USER BY ID
        public Response fetchUserById(@PathParam("userId") Long id) {
                UserDetailsDTO userDetailsDTO = this.userService.getUserDTOById(id);
                Log.info("Returning User " + userDetailsDTO.getName() + " with ID " + id);
                return Response.ok(userDetailsDTO).build();

        }

        @GET
        @Path("/name:{userName}")
        @Produces(MediaType.APPLICATION_JSON)
        @APIResponse(responseCode = "200", description = "User by Name", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UserDetailsDTO.class)))
        @APIResponse(responseCode = "404", description = "User not found")
        // GET USER BY NAME
        public Response fetchUsersByName(@PathParam("userName") String name) {

                List<UserDetailsDTO> users = this.userService.getUsersByName(name);
                for (UserDetailsDTO user : users) {
                        Log.info("Returning founded User by Name " + name + " with ID " + user.getId());
                }
                return Response.ok(users).build();

        }

        @GET
        @Path("/email:{userEmail}")
        @Produces(MediaType.APPLICATION_JSON)
        @APIResponse(responseCode = "200", description = "User by Email", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UserDetailsDTO.class)))
        @APIResponse(responseCode = "404", description = "User not found")
        // GET USER BY EMAIL
        public Response fetchUserByEmail(@PathParam("userEmail") String email) {
                UserDetailsDTO userDetailsDTO = this.userService.getUserByEmail(email);
                Log.info("Returning User " + userDetailsDTO.getName() + " found by Email " + email + " with ID "
                                + userDetailsDTO.getId());
                return Response.ok(userDetailsDTO).build();

        }

        @GET
        @Path("/login")
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public Response login(LoginRequest loginRequest) {
                boolean isLoggedIn = this.userService.login(loginRequest);
                Log.info("User " + loginRequest.getEmail() + " logged in");
                return Response.ok(isLoggedIn).build();
        }

        @POST
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        @APIResponse(responseCode = "201", description = "User created", content = @Content(schema = @Schema(implementation = UserBaseDTO.class)))
        // ADD USES
        public Response createNewUser(
                        @Valid @jakarta.validation.groups.ConvertGroup(from = Default.class, to = ValidationGroups.Create.class) UserCreateRequest userCreateRequest) {
                Log.info("Received UserRequest: name=" + userCreateRequest.getName() + ", email="
                                + userCreateRequest.getEmail());

                UserBaseDTO userBaseDTO = new UserBaseDTO();
                userBaseDTO.setName(userCreateRequest.getName());
                userBaseDTO.setAge(userCreateRequest.getAge());
                userBaseDTO.setEmail(userCreateRequest.getEmail());
                userBaseDTO.setPassword(userCreateRequest.getPassword());
                userBaseDTO.setAddress(userCreateRequest.getAddress());
                userBaseDTO.setPhone(userCreateRequest.getPhone());
                userBaseDTO.setGender(userCreateRequest.getGender());
                userBaseDTO.setDateOfBirth(userCreateRequest.getDateOfBirth());

                UserBaseDTO createdUser = this.userService.addUser(userBaseDTO);
                Log.info("Adding User " + createdUser.getName() + " with ID " + createdUser.getId());
                return Response.status(Response.Status.CREATED).entity(createdUser).build();
        }

        @PUT
        @Path("/{userId}")
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        @APIResponse(responseCode = "200", description = "User updated", content = @Content(schema = @Schema(implementation = UserBaseDTO.class)))
        @APIResponse(responseCode = "404", description = "User not found")
        // UPDATE USER
        public Response updateUser(
                        @PathParam("userId") Long id,
                        @Valid @jakarta.validation.groups.ConvertGroup(from = Default.class, to = ValidationGroups.Update.class) UserRequest userRequest) {

                UserBaseDTO userDTO = new UserBaseDTO(id, userRequest.getName(), userRequest.getAge(),
                                userRequest.getEmail(),
                                userRequest.getAddress(), userRequest.getPhone(),
                                userRequest.getGender(), userRequest.getDateOfBirth());
                userDTO.setUpdatedAt(LocalDateTime.now()); // Update the updatedAt field
                UserBaseDTO updateUser = this.userService.updateUser(id, userDTO);
                Log.info("Updating User " + userDTO.getName() + " with ID " + id);
                return Response.ok(updateUser).build();

        }

        @PUT
        @Path("/{id}/change-password")
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public Response changePassword(@PathParam("id") Long id, PasswordChangeRequest passwordChangeRequest) {
                userService.changePassword(id, passwordChangeRequest);
                UserBaseDTO updateUser = userService.getUserBaseDTOById(id);
                Log.info("Changing password for User " + updateUser.getName() + " with ID " + id);
                return Response.ok(updateUser).build();

        }

        @DELETE
        @Path("/{userId}")
        @Produces(MediaType.APPLICATION_JSON)
        @APIResponses({
                        @APIResponse(responseCode = "200", description = "User deleted", content = @Content(schema = @Schema(implementation = UserDetailsDTO.class))),
                        @APIResponse(responseCode = "404", description = "User not found")
        })
        // REMOVE USER
        public Response removeUser(@PathParam("userId") Long id) {
                UserDetailsDTO userDetailsDTO = this.userService.getUserDTOById(id);
                this.userService.deleteUser(id);
                Log.info("Deleting User " + userDetailsDTO.getName() + " with ID " + id);
                return Response.ok(userDetailsDTO).build();

        }

}
