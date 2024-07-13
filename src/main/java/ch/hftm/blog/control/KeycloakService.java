
package ch.hftm.blog.control;

import ch.hftm.blog.dto.UserBaseDTO;
import ch.hftm.blog.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class KeycloakService {

    private static final String KEYCLOAK_URL = "http://localhost:8080/auth/admin/realms/quarkus/quarkus-app";
    private static final String ADMIN_TOKEN = "Bearer " + "secret"; // Ersetzen Sie <admin-token> durch den tatsächlichen Admin-Token

    public void createUserInKeycloak(UserBaseDTO userDTO) {
        Client client = ClientBuilder.newClient();
        String keycloakUserPayload = "{"
                + "\"username\": \"" + userDTO.getEmail() + "\","
                + "\"email\": \"" + userDTO.getEmail() + "\","
                + "\"enabled\": true,"
                + "\"credentials\": [{"
                + "\"type\": \"password\","
                + "\"value\": \"" + userDTO.getPassword() + "\","
                + "\"temporary\": false"
                + "}]"
                + "}";

        Response response = client.target(KEYCLOAK_URL)
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", ADMIN_TOKEN)
                .post(Entity.json(keycloakUserPayload));

        if (response.getStatus() != 201) {
            throw new RuntimeException("Failed to create user in Keycloak: " + response.getStatus());
        }

        client.close();
    }

    public void deleteUserInKeycloak(User user) {
        Client client = ClientBuilder.newClient();

        Response response = client.target(KEYCLOAK_URL + "/" + user.getId())
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", ADMIN_TOKEN)
                .delete();

        if (response.getStatus() != 204) {
            throw new RuntimeException("Failed to delete user in Keycloak: " + response.getStatus());
        }

        client.close();
    }
}
