import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

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
        // Получаем заранее подготовленного активного пользователя
        UserData user = DataGenerator.getRegisteredActiveUser();

        given()
                .body(user)
        .when()
                .post("/api/auth")
        .then()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Test
    @DisplayName("Should show an error for a blocked registered user")
    void shouldNotLogInBlockedUser() {
        // Получаем заранее подготовленного заблокированного пользователя
        UserData user = DataGenerator.getRegisteredBlockedUser();

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
        // Получаем пользователя с неверным логином
        UserData user = DataGenerator.getUserWithInvalidLogin();

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
        // Получаем пользователя с верным логином, но неверным паролем
        UserData user = DataGenerator.getUserWithInvalidPassword();

        given()
                .body(user)
        .when()
                .post("/api/auth")
        .then()
                .statusCode(401)
                .body("message", equalTo("Неверно указан логин или пароль"));
    }
}
