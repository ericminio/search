package features;

import data.InMemoryDatabase;
import http.Server;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import support.Browsers;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class AlternateLocationsTest {

    private static Server server;
    private static WebDriver browser;
    private static WebElement input;
    private static WebElement button;

    @BeforeClass
    public static void search() throws IOException, InterruptedException {
        server = new Server(8002);
        server.useDatabase(InMemoryDatabase.with("7 pacemaker"));
        server.start();
        browser = Browsers.headless();
        browser.navigate().to( "http://localhost:8002/search" );

        input = browser.findElement(By.cssSelector("input#criteria"));
        button = browser.findElement(By.cssSelector("button#go"));
        input.sendKeys("pacemaker");
        button.click();
        Thread.sleep(600);
    }

    @AfterClass
    public static void stopServer() {
        browser.quit();
        server.stop();
    }

    @Test
    public void displaysFoundCount() throws InterruptedException {
        WebElement spot = browser.findElement(By.cssSelector("#spot-5"));

        assertThat(spot.getText(), equalTo("7"));
    }

    @Test
    public void redsSpotWhenEmpty() throws InterruptedException {
        WebElement spot = browser.findElement(By.cssSelector("#spot-2"));

        assertThat(spot.getText(), equalTo("0"));
        assertThat(spot.getCssValue("background-color"), equalTo("rgba(255, 0, 0, 1)"));
    }

    @Test
    public void tooltipsTheLocation() throws InterruptedException {
        WebElement spot = browser.findElement(By.cssSelector("#spot-5"));

        assertThat(spot.getAttribute("title"), equalTo("location: E-5-5"));
    }
}
