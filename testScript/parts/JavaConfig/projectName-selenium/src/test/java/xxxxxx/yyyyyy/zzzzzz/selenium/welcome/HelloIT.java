package xxxxxx.yyyyyy.zzzzzz.selenium.welcome;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import xxxxxx.yyyyyy.zzzzzz.config.SeleniumContextConfig;
import xxxxxx.yyyyyy.zzzzzz.selenium.FunctionTestSupport;

/**
 * Executes the test for the application home page.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SeleniumContextConfig.class })
public class HelloIT extends FunctionTestSupport {

    /**
     * Asserts that the content of the application home page is "Hello world!".
     */
    @Test
    public void testHelloWorld() throws IOException {

        assertThat(webDriver.findElement(By.id("title")).getText(), is(
                "Hello world!"));
    }
}
