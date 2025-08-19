package xxxxxx.yyyyyy.zzzzzz.selenium.welcome;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import jakarta.inject.Inject;

/**
 * Executes the test for the application home page.
 */
@SpringJUnitConfig(locations = {"classpath:META-INF/spring/seleniumContext.xml"})
public class HelloIT {

    @Inject
    WebDriver webDriver;

    @Value("${selenium.applicationContextUrl}")
    String applicationContextUrl;

    /**
     * Asserts that the content of the application home page is "Hello world!".
     */
    @Test
    public void testHelloWorld() throws IOException {

        webDriver.get(applicationContextUrl);

        assertEquals("Hello world!", webDriver.findElement(By.id("title")).getText());
    }

    /**
     * Quits the driver, closing every associated window.
     */
    @AfterEach
    public void tearDown() {
        webDriver.quit();
    }
}
