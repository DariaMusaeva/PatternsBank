package test;

import data.DataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

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
    }
}
