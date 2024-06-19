package ch.hftm.blog.boundry;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class UserResourceTest {

    @Test
    public void testFetchAllUsers() {
        given()
                .when().get("/user")
                .then()
                .statusCode(200);
    }

    @Test
    public void testAddUser() {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("John Doe");
        userRequest.setAge(30);
        userRequest.setEmail("john.doe@example.com");
        userRequest.setPassword("password123");
        userRequest.setAddress("123 Main St");
        userRequest.setPhone("+41 78 965 26 15");
        userRequest.setGender("male");
        userRequest.setDateOfBirth(LocalDate.of(1993, 5, 15));

        given()
                .contentType(ContentType.JSON)
                .body(userRequest)
                .when().post("/user")
                .then()
                .statusCode(201)
                .body("name", equalTo("John Doe"));
    }

    @Test
    public void testFetchUserById() {
        // Assume a user with ID 1 exists
        given()
                .when().get("/user/byId?userId=1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1));
    }

    @Test
    public void testUpdateUser() {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("John Updated");
        userRequest.setAge(31);
        userRequest.setEmail("john.updated@example.com");
        userRequest.setPassword("newpassword123");
        userRequest.setAddress("456 Main St");
        userRequest.setPhone("+41 78 965 26 16");
        userRequest.setGender("male");
        userRequest.setDateOfBirth(LocalDate.of(1992, 4, 12));

        given()
                .contentType(ContentType.JSON)
                .body(userRequest)
                .when().put("/user/updateUser?userId=1")
                .then()
                .statusCode(200)
                .body("name", equalTo("John Updated"));
    }

    @Test
    public void testRemoveUser() {
        given()
                .when().delete("/user/byId?userId=1")
                .then()
                .statusCode(200);
    }
}
