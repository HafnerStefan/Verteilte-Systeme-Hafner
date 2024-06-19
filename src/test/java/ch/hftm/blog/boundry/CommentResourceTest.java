/*package ch.hftm.blog.boundry;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class CommentResourceTest {

  private Long userId;
  private Long blogId;

  @BeforeEach
  public void setup() {
    // Assuming that a user and a blog already exist in the database
    userId = 1L; // replace with actual user ID
    blogId = 1L; // replace with actual blog ID
  }

  @Test
  public void testGetComments() {
    given()
        .when().get("/comments")
        .then()
        .statusCode(200);
  }

  @Test
  public void testAddComment() {
    CommentRequest newComment = new CommentRequest("Nice post!", userId, blogId);

    given()
        .contentType(ContentType.JSON)
        .body(newComment)
        .when().post("/comments")
        .then()
        .statusCode(201);
  }

  @Test
  public void testUpdateComment() {
    CommentRequest updatedComment = new CommentRequest("Updated comment text", userId, blogId);

    given()
        .contentType(ContentType.JSON)
        .body(updatedComment)
        .when().put("/comments/1") // assuming comment with ID 1 exists
        .then()
        .statusCode(200);
  }

  @Test
  public void testDeleteComment() {
    given()
        .when().delete("/comments/1") // assuming comment with ID 1 exists
        .then()
        .statusCode(204);
  }
}
*/