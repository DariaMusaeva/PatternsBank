package data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {

    private DataGenerator() {
    }

    public static String generateDate(int set) {
        return LocalDate.now().plusDays(set).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCity() {
        var cities = new String[]{"Москва", "Пермь", "Санкт-Петербург", "Архангельск", "Саратов",
                "Омск", "Тверь", "Тюмень", "Курск", "Казань", "Екатеринбург", "Челябинск", "Ижевск"};
        return cities[new Random().nextInt(cities.length)];
    }

    public static String generateInvalidCity() {
        var invalidCities = new String[]{"1234", "Сочи", "??:;;г", "Sochi"};
        return invalidCities[new Random().nextInt(invalidCities.length)];
    }

    public static String generateEmptyCity() {
        String emptyCity = new String();
        return emptyCity;
    }

    public static String generateName(String locale) {
        var faker = new Faker(new Locale(locale));
        return faker.name().fullName();
    }

    public static String generateInvalidName() {
        var faker = new Faker();
        return faker.funnyName().name();
    }

    public static String generateEmptyName() {
        String emptyName = new String();
        return emptyName;
    }

    public static String generatePhone(String locale) {
        var faker = new Faker(new Locale(locale));
        return faker.phoneNumber().phoneNumber();
    }

    public static String generateEmptyPhone() {
        String emptyPhone = new String();
        return emptyPhone;
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            return new UserInfo(generateCity(), generateName(locale), generatePhone(locale));
        }

        public static InvalidUser generateInvalidUser(String locale) {
            return new InvalidUser(generateInvalidCity(), generateInvalidName(), generatePhone(locale));
        }

        public static EmptyUser generateEmptyUser() {
            return new EmptyUser(generateEmptyCity(), generateEmptyName(), generateEmptyPhone());
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }

    @Value
    public static class InvalidUser {
        String invalidCity;
        String funnyName;
        String phone;
    }

    @Value
    public static class EmptyUser {
        String emptyCity;
        String emptyName;
        String emptyPhone;
    }
}