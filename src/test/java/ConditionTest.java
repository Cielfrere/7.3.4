import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.closeWebDriver;

public class ConditionTest {

    @BeforeAll
    public static void setup() {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 10000;
    }

    @AfterAll
    public static void tearDown() {
        closeWebDriver();
    }

    public void openClickStart(String url) {
        Selenide.open(url);
        $("#start button").click();
    }



    @Test
    @DisplayName("Ожидание появления элемента")
    public void elementElusiveTest() {
        openClickStart("https://the-internet.herokuapp.com/dynamic_loading/1");
        $("#finish").shouldHave(Condition.text("Hello World!"), Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Ожидание исчезновения элемента")
    public void elementFadeAwayTest() {
        openClickStart("https://the-internet.herokuapp.com/dynamic_loading/2");
        $("#loading").should(Condition.disappear);
    }

    @Test
    @DisplayName("Ожидание определенного текста")
    public void checkTextPresence() {
        Selenide.open("https://the-internet.herokuapp.com/dynamic_loading/1");
        SelenideElement startButton = Selenide.$("#start button");
        startButton.click();
        SelenideElement helloWorld = Selenide.$("#finish h4");
        helloWorld.shouldHave(Condition.text("Hello World!"));
    }

    @Test
    @DisplayName("Ожидание определенного значения")
    public void valueTest() {
        Selenide.open("https://the-internet.herokuapp.com/dropdown");
        $("#dropdown").selectOption("Option 1");
        $("#dropdown").shouldHave(Condition.value("1"));
    }

    @Test
    @DisplayName("Ожидание атрибута")
    public void attributeTest() {
        Selenide.open("https://the-internet.herokuapp.com/login");
        $("#username").shouldHave(Condition.attribute("type", "text"));
    }

    @Test
    @DisplayName("Таймауты")
    public void timeoutTest() {
        com.codeborne.selenide.Configuration.timeout = 10000;
        openClickStart("https://the-internet.herokuapp.com/dynamic_loading/2");
        $("#finish").shouldHave(Condition.text("Hello World!"));
    }

    @Test
    @DisplayName("Sleep")
    public void checkSleep() {
        Selenide.open("https://the-internet.herokuapp.com/dynamic_loading/1");
        SelenideElement startButton = Selenide.$("#start button");
        startButton.click();
        Selenide.sleep(5000);
        SelenideElement helloWorld = Selenide.$("#finish h4");
        helloWorld.shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Ожидание видимости элемента")
    public void elementLoadTest() {
        Selenide.open("https://the-internet.herokuapp.com/");
        $("a[href='/abtest']").shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Ожидание изменения текста элемента")
    public void textChangeTest() {
        Selenide.open("https://the-internet.herokuapp.com/dynamic_controls");
        $("#input-example button").click();
        $("#input-example button").shouldHave(Condition.text("Disable"));
    }

    @Test
    @DisplayName("Ожидание элемента в определенном состоянии")
    public void elementStateTest() {
        Selenide.open("https://the-internet.herokuapp.com/challenging_dom");
        $(".success").shouldBe(Condition.visible);
    }
}
