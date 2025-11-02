import com.github.javafaker.Faker;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {

    private DataGenerator() {}

    public static UserData generateUser() {
        Faker faker = new Faker(new Locale("ru"));
        return new UserData(
                faker.address().city(),
                faker.name().lastName() + " " + faker.name().firstName(),
                faker.phoneNumber().phoneNumber()
        );
    }

    public static String generateDate(int daysToAdd) {
        return LocalDate.now().plusDays(daysToAdd).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    // ВОТ НОВЫЙ МЕТОД, ТЕПЕРЬ ОН ВНУТРИ КЛАССА
    public static RegistrationDto generateRegistration(String status) {
        Faker faker = new Faker(new Locale("en"));
        return new RegistrationDto(
                faker.name().username(),
                faker.internet().password(),
                status
        );
    }

}