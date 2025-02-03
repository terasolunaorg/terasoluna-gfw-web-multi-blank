package xxxxxx.yyyyyy.zzzzzz.selenium.welcome;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import java.io.IOException;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import jakarta.inject.Inject;

import xxxxxx.yyyyyy.zzzzzz.config.SeleniumContextConfig;

/**
 * Executes the test for the application home page.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SeleniumContextConfig.class})
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

        assertThat(webDriver.findElement(By.id("title")).getText(), is("Hello world!"));
    }

    /**
     * Quits the driver, closing every associated window.
     */
    @After
    public void tearDown() {
        webDriver.quit();
    }
}
