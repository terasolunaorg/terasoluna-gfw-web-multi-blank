package xxxxxx.yyyyyy.zzzzzz.selenium.welcome;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import xxxxxx.yyyyyy.zzzzzz.selenium.FunctionTestSupport;

/**
 * Executes the test for the application home page.
 */
@SpringJUnitConfig(locations = {"classpath:META-INF/spring/seleniumContext.xml"})
public class HelloIT extends FunctionTestSupport {

    /**
     * Asserts that the content of the application home page is "Hello world!".
     */
    @Test
    public void testHelloWorld() throws IOException {

        assertThat(webDriver.findElement(By.id("title")).getText()).isEqualTo("Hello world!");
    }
}
