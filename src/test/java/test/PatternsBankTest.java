package test;

import com.codeborne.selenide.Condition;
import data.DataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class PatternsBankTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("shouldRescheduleMeeting")
    void shouldRescheduleMeeting() {
        DataGenerator.UserInfo validUser = DataGenerator.Registration.generateUser("ru");
        int daysForFirstMeeting = 4;
        String firstMeetingDate = DataGenerator.generateDate(daysForFirstMeeting);
        int daysForSecondMeeting = 7;
        String secondMeetingDate = DataGenerator.generateDate(daysForSecondMeeting);
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(firstMeetingDate);
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $(byText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(5));
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(text("Встреча успешно запланирована на " + firstMeetingDate))
                .shouldBe(Condition.visible, Duration.ofSeconds(5));
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(secondMeetingDate);
        $(byText("Запланировать")).click();
        $("[data-test-id='replan-notification'] .notification__content")
                .shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать? "))
                .shouldBe(Condition.visible, Duration.ofSeconds(5));
        $(byText("Перепланировать")).click();
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(text("Встреча успешно запланирована на " + secondMeetingDate))
                .shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Test
    @DisplayName("shouldTestFormWithoutAgreement")
    void shouldTestFormWithoutAgreement() {
        DataGenerator.UserInfo validUser = DataGenerator.Registration.generateUser("ru");
        int daysForFirstMeeting = 4;
        String firstMeetingDate = DataGenerator.generateDate(daysForFirstMeeting);
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(firstMeetingDate);
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $(byText("Запланировать")).click();
        $("[data-test-id=agreement] .checkbox__text")
                .shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("shouldTestInvalidName")
    void shouldTestInvalidName() {
        DataGenerator.InvalidUser invalidUser = DataGenerator.Registration.generateInvalidUser("eng");
        DataGenerator.UserInfo validUser = DataGenerator.Registration.generateUser("ru");
        int daysForFirstMeeting = 4;
        String firstMeetingDate = DataGenerator.generateDate(daysForFirstMeeting);
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(firstMeetingDate);
        $("[data-test-id=name] input").setValue(invalidUser.getFunnyName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $("[data-test-id='name'] .input__sub")
                .shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("shouldTestInvalidCity")
    void shouldTestInvalidCity() {
        DataGenerator.InvalidUser invalidUser = DataGenerator.Registration.generateInvalidUser("eng");
        DataGenerator.UserInfo validUser = DataGenerator.Registration.generateUser("ru");
        int daysForFirstMeeting = 4;
        String firstMeetingDate = DataGenerator.generateDate(daysForFirstMeeting);
        $("[data-test-id=city] input").setValue(invalidUser.getInvalidCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(firstMeetingDate);
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $("[data-test-id='city'] .input__sub")
                .shouldHave(text("Доставка в выбранный город недоступна"))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("shouldTestEmptyCity")
    void shouldTestEmptyCity() {
        DataGenerator.EmptyUser emptyUser = DataGenerator.Registration.generateEmptyUser();
        DataGenerator.UserInfo validUser = DataGenerator.Registration.generateUser("ru");
        int daysForFirstMeeting = 4;
        String firstMeetingDate = DataGenerator.generateDate(daysForFirstMeeting);
        $("[data-test-id=city] input").setValue(emptyUser.getEmptyCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(firstMeetingDate);
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $("[data-test-id='city'] .input__sub")
                .shouldHave(text("Поле обязательно для заполнения"))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("shouldTestEmptyDate")
    void shouldTestEmptyDate() {
        DataGenerator.UserInfo validUser = DataGenerator.Registration.generateUser("ru");
        int daysForFirstMeeting = 4;
        String firstMeetingDate = DataGenerator.generateDate(daysForFirstMeeting);
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $("[data-test-id='date'] .input__sub")
                .shouldHave(text("Неверно введена дата"))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("shouldTestEmptyName")
    void shouldTestEmptyName() {
        DataGenerator.EmptyUser emptyUser = DataGenerator.Registration.generateEmptyUser();
        DataGenerator.UserInfo validUser = DataGenerator.Registration.generateUser("ru");
        int daysForFirstMeeting = 4;
        String firstMeetingDate = DataGenerator.generateDate(daysForFirstMeeting);
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(firstMeetingDate);
        $("[data-test-id=name] input").setValue(emptyUser.getEmptyName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $("[data-test-id='name'] .input__sub")
                .shouldHave(text("Поле обязательно для заполнения"))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("shouldTestEmptyPhone")
    void shouldTestEmptyPhone() {
        DataGenerator.EmptyUser emptyUser = DataGenerator.Registration.generateEmptyUser();
        DataGenerator.UserInfo validUser = DataGenerator.Registration.generateUser("ru");
        int daysForFirstMeeting = 4;
        String firstMeetingDate = DataGenerator.generateDate(daysForFirstMeeting);
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(firstMeetingDate);
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(emptyUser.getEmptyPhone());
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $("[data-test-id='phone'] .input__sub")
                .shouldHave(text("Поле обязательно для заполнения"))
                .shouldBe(Condition.visible);
    }
}
