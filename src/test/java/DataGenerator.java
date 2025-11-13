import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;

import java.util.Locale;

@UtilityClass
public class DataGenerator {

    private static final Faker faker = new Faker(new Locale("ru"));

    /**
     * Возвращает данные заранее зарегистрированного, активного пользователя.
     * Эти данные подходят для успешного входа в систему.
     */
    public static UserData getRegisteredActiveUser() {
        return new UserData("vasya", "password", "active");
    }

    /**
     * Возвращает данные заранее зарегистрированного, но заблокированного пользователя.
     * Эти данные подходят для проверки входа заблокированного пользователя.
     */
    public static UserData getRegisteredBlockedUser() {
        return new UserData("petya", "password", "blocked");
    }

    /**
     * Возвращает данные с неверным логином.
     * Пользователя с таким логином не существует в системе.
     */
    public static UserData getUserWithInvalidLogin() {
        // Генерируем случайный пароль и статус, но логин заведомо неверный
        return new UserData(faker.name().username(), faker.internet().password(), "active");
    }

    /**
     * Возвращает данные зарегистрированного пользователя, но с неверным паролем.
     */
    public static UserData getUserWithInvalidPassword() {
        // Логин правильный, а пароль - нет
        return new UserData("vasya", faker.internet().password(), "active");
    }
}
