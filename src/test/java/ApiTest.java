import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ApiTest {

    @BeforeEach
    void setUp() {
        given()
            .baseUri("http://localhost")
            .port(9999)
            .contentType("application/json");
    }

    @Test
    @DisplayName("Should successfully log in with an active registered user")
    void shouldLogInActiveUser() {
        UserData user = DataGenerator.generateAndRegisterUser("active");
        given()
            .body(user)
        .when()
            .post("/api/auth")
        .then()
            .statusCode(200)
            .body("token", org.hamcrest.Matchers.notNullValue());
    }

    @Test
    @DisplayName("Should show an error for a blocked registered user")
    void shouldNotLogInBlockedUser() {
        UserData user = DataGenerator.generateAndRegisterUser("blocked");
        given()
            .body(user)
        .when()
            .post("/api/auth")
        .then()
            .statusCode(401)
            .body("message", equalTo("Пользователь заблокирован"));
    }

    @Test
    @DisplayName("Should show an error with an invalid login")
    void shouldNotLogInWithInvalidLogin() {
        UserData user = DataGenerator.generateUserWithInvalidLogin("active");
        given()
            .body(user)
        .when()
            .post("/api/auth")
        .then()
            .statusCode(401)
            .body("message", equalTo("Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should show an error with an invalid password")
    void shouldNotLogInWithInvalidPassword() {
        UserData user = DataGenerator.generateUserWithInvalidPassword("active");
        given()
            .body(user)
        .when()
            .post("/api/auth")
        .then()
            .statusCode(401)
            .body("message", equalTo("Неверно указан логин или пароль"));
    }
}
