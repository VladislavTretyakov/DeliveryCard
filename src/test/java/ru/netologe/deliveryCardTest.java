package ru.netologe;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class deliveryCardTest {
    @BeforeEach
    public void eachTest() {
        Configuration.holdBrowserOpen = true;
        Configuration.startMaximized = true;
        Configuration.browserSize = "900x900";
        open("http://localhost:9999/");
    }

    public String generateDate(int addDay) {
        return LocalDate.now().plusDays(addDay).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void actualValue() {
        String planDate = generateDate(3);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planDate);
        $("span[data-test-id=name] input").setValue("Владислав Третьяков");
        $("span[data-test-id=phone] input").setValue("+79994445566");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(By.className("button")).click();
        $(".notification__content").shouldBe(Condition.visible, Duration.ofSeconds(15)).shouldHave(Condition.exactText("Встреча успешно забронирована на " + planDate));

    }

    @Test
    void dateInTheFuture() {
        String planDate = generateDate(14);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planDate);
        $("span[data-test-id=name] input").setValue("Владислав Третьяков");
        $("span[data-test-id=phone] input").setValue("+79994445566");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(By.className("button")).click();
        $(".notification__content").shouldBe(Condition.visible, Duration.ofSeconds(15)).shouldHave(Condition.exactText("Встреча успешно забронирована на " + planDate));

    }

    @Test
    void dateUpToThreeDays() {
        String planDate = generateDate(1);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planDate);
        $("span[data-test-id=name] input").setValue("Владислав Третьяков");
        $("span[data-test-id=phone] input").setValue("+79994445566");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(By.className("button")).click();
        $(By.cssSelector("[data-test-id='date'] span.input__sub")).shouldBe(Condition.visible).shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void pastDate() {
        String planDate = generateDate(-1);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planDate);
        $("span[data-test-id=name] input").setValue("Владислав Третьяков");
        $("span[data-test-id=phone] input").setValue("+79994445566");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(By.className("button")).click();
        $(By.cssSelector("[data-test-id='date'] span.input__sub")).shouldBe(Condition.visible).shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void invalidCityField() {
        String planDate = generateDate(4);

        $("[data-test-id=city] input").setValue("Тара");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planDate);
        $("span[data-test-id=name] input").setValue("Владислав Третьяков");
        $("span[data-test-id=phone] input").setValue("+79994445566");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(By.className("button")).click();
        $(By.cssSelector("[data-test-id='city'] span.input__sub")).shouldBe(Condition.visible).shouldHave(Condition.exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void surnameFieldInLatin() {
        String planDate = generateDate(4);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planDate);
        $("span[data-test-id=name] input").setValue("Tretyakov Vkadislav");
        $("span[data-test-id=phone] input").setValue("+79994445566");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(By.className("button")).click();
        $(By.cssSelector("[data-test-id='name'] span.input__sub")).shouldBe(Condition.visible).shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void invalidPhoneNumber() {
        String planDate = generateDate(4);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planDate);
        $("span[data-test-id=name] input").setValue("Владислав Третьяков");
        $("span[data-test-id=phone] input").setValue("+799944455667");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(By.className("button")).click();
        $(By.cssSelector("[data-test-id='phone'] span.input__sub")).shouldBe(Condition.visible).shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shortPhoneNumber() {
        String planDate = generateDate(4);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planDate);
        $("span[data-test-id=name] input").setValue("Владислав Третьяков");
        $("span[data-test-id=phone] input").setValue("+7999444556");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(By.className("button")).click();
        $(By.cssSelector("[data-test-id='phone'] span.input__sub")).shouldBe(Condition.visible).shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void noCheckbox() {
        String planDate = generateDate(4);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planDate);
        $("span[data-test-id=name] input").setValue("Владислав Третьяков");
        $("span[data-test-id=phone] input").setValue("+79994445566");
        $("button.button").click();
        $(By.className("button")).click();
        $(By.cssSelector("[data-test-id=agreement].input_invalid")).shouldBe(Condition.visible);
    }

    @Test
    void emptyForm() {
        $(By.className("button")).click();
        $(By.cssSelector("[data-test-id=city] span.input__sub")).shouldBe(Condition.visible).shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void emptyPhoneLine() {
        String planDate = generateDate(4);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planDate);
        $("span[data-test-id=name] input").setValue("Владислав Третьяков");
        $("button.button").click();
        $(By.className("button")).click();
        $(By.cssSelector("[data-test-id=phone] span.input__sub")).shouldBe(Condition.visible).shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void emptyStringCity() {
        String planDate = generateDate(4);

        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planDate);
        $("span[data-test-id=name] input").setValue("Владислав Третьяков");
        $("span[data-test-id=phone] input").setValue("+79994445566");
        $("button.button").click();
        $(By.className("button")).click();
        $(By.cssSelector("[data-test-id=city] span.input__sub")).shouldBe(Condition.visible).shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void emptyStringLastName() {
        String planDate = generateDate(4);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planDate);
        $("span[data-test-id=phone] input").setValue("+79994445566");
        $("button.button").click();
        $(By.className("button")).click();
        $(By.cssSelector("[data-test-id=name] span.input__sub")).shouldBe(Condition.visible).shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }


}
