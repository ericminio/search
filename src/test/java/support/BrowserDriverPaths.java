package support;

import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringEndsWith.endsWith;

public class BrowserDriverPaths {

    @Test
    public void geckoDriverPathIsKnown() {
        assertThat(BrowserDriverPaths.firefox(), endsWith("geckodriver.exe"));
        assertThat(new File(BrowserDriverPaths.firefox()).exists(), equalTo(true));
    }

    @Test
    public void chromeDriverPathIsKnown() {
        assertThat(BrowserDriverPaths.chrome(), endsWith("chromedriver.exe"));
        assertThat(new File(BrowserDriverPaths.chrome()).exists(), equalTo(true));
    }

    public static String chrome() {
        File current = new File("");
        Path path = Paths.get("src", "test", "resources", "chromedriver.exe");

        return current.getAbsolutePath() + File.separator + path.toString();
    }

    public static String firefox() {
        File current = new File("");
        Path path = Paths.get("src", "test", "resources", "geckodriver.exe");

        return current.getAbsolutePath() + File.separator + path.toString();
    }
}
