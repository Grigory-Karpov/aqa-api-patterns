import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;

import java.util.Locale;

import static io.restassured.RestAssured.given;

@UtilityClass
public class DataGenerator {

    private static final Faker faker = new Faker(new Locale("ru"));

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static UserData generateAndRegisterUser(String status) {
        UserData user = new UserData(
                faker.name().username(),
                faker.internet().password(),
                status
        );
        given()
                .spec(requestSpec)
                .body(user)
        .when()
                .post("/api/system/users")
        .then()
                .statusCode(200);
        return user;
    }

    public static UserData generateUserWithInvalidLogin(String status) {
        String password = faker.internet().password();
        return new UserData(
                "vasya-invalid", // Заведомо несуществующий логин
                password,
                status
        );
    }

    public static UserData generateUserWithInvalidPassword(String status) {
        UserData user = generateAndRegisterUser(status);
        // Возвращаем того же пользователя, но с новым, неверным паролем
        return new UserData(user.getLogin(), faker.internet().password(), user.getStatus());
    }
}
