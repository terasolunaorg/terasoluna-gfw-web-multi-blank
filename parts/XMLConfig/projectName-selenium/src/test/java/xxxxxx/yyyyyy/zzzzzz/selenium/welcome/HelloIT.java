package xxxxxx.yyyyyy.zzzzzz.selenium.welcome;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import xxxxxx.yyyyyy.zzzzzz.selenium.FunctionTestSupport;

/**
 * Executes the test for the application home page.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:META-INF/spring/seleniumContext.xml" })
public class HelloIT extends FunctionTestSupport{

    /**
     * Asserts that the content of the application home page is "Hello world!".
     */
    @Test
    public void testHelloWorld() throws IOException {

        assertThat(webDriver.findElement(By.id("title")).getText(),
                is("Hello world!"));
    }
}
