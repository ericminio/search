package diagnose;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class JunitReadyTest {

    @Test
    public void canAssert() {
        assertThat(1+1, equalTo(2));
    }

}
