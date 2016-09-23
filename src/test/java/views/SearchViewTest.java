package views;

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
import static org.junit.Assert.assertThat;

public class SearchViewTest {

    private Server server;
    private WebDriver browser;

    @Before
    public void startServer() throws IOException {
        server = new Server(8003);
        server.useDatabase(InMemoryDatabase.with("7 pacemaker"));
        server.start();
        browser = Browsers.headless();
        browser.navigate().to( "http://localhost:8003/search" );
    }

    @After
    public void stopServer() {
        browser.quit();
        server.stop();
    }

    @Test
    public void spotsAreHiddenByDefault() {
        List<WebElement> spots = browser.findElements(By.cssSelector(".spot"));

        assertThat(spots.size(), equalTo(5));
        for(WebElement spot:spots) {
            assertThat(spot.getCssValue("display"), equalTo("none"));
        }
    }
}
