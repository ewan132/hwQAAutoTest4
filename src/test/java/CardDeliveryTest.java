import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    private String localDateFromCard (long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }



    @Test
    void shouldDeliveryCard(){

         open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Владимир");
        String date = localDateFromCard(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord( Keys.LEFT_CONTROL, "a"), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Кузнецов Иван");
        $("[data-test-id='phone'] input").setValue("+79150000000");
        $("[data-test-id='agreement']").click();
        $$(".button__content").last().click();
        $(".notification__content").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(exactText("Встреча успешно забронирована на " + date));
    }
}
