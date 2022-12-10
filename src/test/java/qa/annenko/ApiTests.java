package qa.annenko;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.core.Is.is;

public class ApiTests {

    @Test
    public void totalOfGetListOfUserTest() {
        given()
                .log().uri()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().body()
                .statusCode(200)
                .body("total", is(12));
    }

    @Test
    public void itemsOfGetListOfUserTest() {
        given()
                .log().uri()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().body()
                .statusCode(200)
                .body("data.last_name", hasItems("Lawson", "Ferguson", "Funke"));
    }

    @Test
    public void singleUserNotFoundTest() {
        given()
                .log().uri()
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .log().body()
                .statusCode(404);
    }

    @Test
    public void createUserTest() {

        String requestBody = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";

        given()
                .log().uri()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }

    @Test
    public void updateUserTest() {

        String requestBody = "{ \"name\": \"morpheus\", \"job\": \"zion resident\" }";

        given()
                .log().uri()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .log().body()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }

    @Test
    public void deleteUserTest() {

        given()
                .log().uri()
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .log().body()
                .statusCode(204);
    }

    @Test
    public void unSuccessfulLoginTest() {

        String requestBody = "{ \"email\": \"peter@klaven\" }";

        given()
                .log().uri()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }
}