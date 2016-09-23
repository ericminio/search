package features;

import http.Server;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import support.Browsers;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class GreetingsTest {

    private static Server server;
    private static WebDriver browser;
    private static WebElement element;

    @BeforeClass
    public static void startServer() throws IOException {
        server = new Server(8001);
        server.start();
        browser = Browsers.headless();
        browser.navigate().to( "http://localhost:8001/" );
        element = browser.findElement(By.id("greetings"));
    }

    @AfterClass
    public static void stopServer() {
        browser.quit();
        server.stop();
    }

    @Test
    public void saysHello() {
        assertThat(element.getText(), equalTo("Hello World"));
    }

    @Test
    public void saysItInGreen() {
        assertThat(element.getCssValue("color"), equalTo("rgba(0, 255, 0, 1)"));
    }
}
