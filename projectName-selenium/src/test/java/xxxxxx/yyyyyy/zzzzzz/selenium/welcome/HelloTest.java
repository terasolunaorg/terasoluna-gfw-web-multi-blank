package xxxxxx.yyyyyy.zzzzzz.selenium.welcome;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Executes the test for the application home page.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:META-INF/spring/seleniumContext.xml" })
public class HelloTest {

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

        assertThat(webDriver.findElement(By.id("title")).getText(),
                is("Hello world!"));

        webDriver.close();
    }
}
