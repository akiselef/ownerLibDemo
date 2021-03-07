package tests;

import com.codeborne.selenide.Configuration;
import config.WebConfig;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class SimpleConfigTest {

    @BeforeAll
    static void browserSetup() {
        final WebConfig webconfig = ConfigFactory
                .create(WebConfig.class,System.getProperties());
        Configuration.browser = webconfig.getBrowser();
        Configuration.browserVersion = webconfig.getBrowserVersion();

        if (webconfig.getSelenoidUrl() != null) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("enableVNC", true);
            capabilities.setCapability("enableVideo", true);
            Configuration.browserCapabilities = capabilities;
            Configuration.remote = webconfig.getSelenoidUrl();
        }
    }


    @Test
    public void basicTest() {
        open("https://github.com/selenide/selenide");
        $("[data-tab-item=i4wiki-tab]").click();
        $x("//a[text()='SoftAssertions']").shouldBe(visible);
        $x("//a[text()='SoftAssertions']").click();
        $("#wiki-body").shouldHave(text("Using JUnit5 extend test class:"));
    }
}
