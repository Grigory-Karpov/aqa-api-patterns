import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;

import java.util.Locale;

@UtilityClass
public class DataGenerator {

    private static final Faker faker = new Faker(new Locale("ru"));

    public static UserData getRegisteredActiveUser() {
        return new UserData("vasya", "password", "active");
    }

    public static UserData getRegisteredBlockedUser() {
        return new UserData("petya", "password", "blocked");
    }

    public static UserData getUserWithInvalidLogin() {
        return new UserData(faker.name().username(), faker.internet().password(), "active");
    }

    public static UserData getUserWithInvalidPassword() {
        return new UserData("vasya", faker.internet().password(), "active");
    }
}
