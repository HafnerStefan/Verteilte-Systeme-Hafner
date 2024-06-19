package ch.hftm.blog.boundry;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class BlogResourceTest {

    @BeforeEach
    public void setup() {
        // Erstelle einen User, der für die Tests verwendet wird
        given()
                .contentType(ContentType.JSON)
                .body("{\"name\": \"Test User\"}")
                .when().post("/user")
                .then()
                .statusCode(201);
    }

    @Test
    public void testFetchAllBlogs() {
        given()
                .when().get("/blog")
                .then()
                .statusCode(200);
    }

    @Test
    public void testCreateNewBlog() {
        BlogRequest blogRequest = new BlogRequest();
        blogRequest.setTitle("New Blog");
        blogRequest.setText("This is a new blog");
        blogRequest.setUserId(1L); // ID des vorher erstellten Users

        given()
                .contentType(ContentType.JSON)
                .body(blogRequest)
                .when().post("/blog")
                .then()
                .statusCode(201)
                .body("title", equalTo("New Blog"));
    }

    @Test
    public void testFetchBlogById() {
        // Erstelle ein Blog, das für den Test verwendet wird
        BlogRequest blogRequest = new BlogRequest();
        blogRequest.setTitle("Test Blog");
        blogRequest.setText("This is a test blog");
        blogRequest.setUserId(1L);

        given()
                .contentType(ContentType.JSON)
                .body(blogRequest)
                .when().post("/blog")
                .then()
                .statusCode(201);

        // Teste das Abrufen des Blogs mit ID 1
        given()
                .when().get("/blog/byId?blogId=1")
                .then()
                .statusCode(200)
                .body("title", equalTo("Test Blog"));
    }

    @Test
    public void testUpdateBlog() {
        // Erstelle ein Blog, das für den Test verwendet wird
        BlogRequest blogRequest = new BlogRequest();
        blogRequest.setTitle("Test Blog");
        blogRequest.setText("This is a test blog");
        blogRequest.setUserId(1L);

        given()
                .contentType(ContentType.JSON)
                .body(blogRequest)
                .when().post("/blog")
                .then()
                .statusCode(201);

        // Update das Blog
        blogRequest.setTitle("Updated Blog");
        blogRequest.setText("This blog has been updated");

        given()
                .contentType(ContentType.JSON)
                .body(blogRequest)
                .when().put("/blog/byId?blogId=1")
                .then()
                .statusCode(200)
                .body("title", equalTo("Updated Blog"));
    }

    @Test
    public void testRemoveBlog() {
        // Erstelle ein Blog, das für den Test verwendet wird
        BlogRequest blogRequest = new BlogRequest();
        blogRequest.setTitle("Test Blog");
        blogRequest.setText("This is a test blog");
        blogRequest.setUserId(1L);

        given()
                .contentType(ContentType.JSON)
                .body(blogRequest)
                .when().post("/blog")
                .then()
                .statusCode(201);

        // Entferne das Blog
        given()
                .when().delete("/blog/byId?blogId=1")
                .then()
                .statusCode(200);
    }
}
