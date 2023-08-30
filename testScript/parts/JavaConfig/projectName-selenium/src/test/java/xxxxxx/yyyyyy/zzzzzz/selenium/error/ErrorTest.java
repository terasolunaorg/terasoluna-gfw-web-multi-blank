package xxxxxx.yyyyyy.zzzzzz.selenium.error;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.time.Duration;

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
import xxxxxx.yyyyyy.zzzzzz.selenium.FunctionTestSupport;

/**
 * Perform an error page rendering test.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SeleniumContextConfig.class })
public class ErrorTest extends FunctionTestSupport{


    @Value("${selenium.applicationContextUrl}")
    private String applicationContextUrl;

    /**
     * Access denied error page test.
     * @throws Exception
     */
    @Test
    public void testAccessDeniedError() throws Exception {

        webDriver.get(applicationContextUrl + "/test/error/accessDeniedError");
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        assertThat(webDriver.getTitle(), is("Access Denied Error!"));
        assertThat(webDriver.findElement(By.tagName("h1")).getText(), is(
                "Access Denied Error!"));
        assertThat(webDriver.findElement(By.className("error")).getText(), is(
                "[e.xx.fw.7003] Access Denied detected!"));
    }

    /**
     * Business error page test.
     * @throws Exception
     */
    @Test
    public void testBusinessError() throws Exception {

        webDriver.get(applicationContextUrl + "/test/error/businessError");
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        assertThat(webDriver.getTitle(), is("Business Error!"));
        assertThat(webDriver.findElement(By.tagName("h1")).getText(), is(
                "Business Error!"));
        assertThat(webDriver.findElement(By.className("error")).getText(), is(
                "[e.xx.fw.8001] Business error occurred!"));
    }

    /**
     * Data access error page test.
     * @throws Exception
     */
    @Test
    public void testDataAccessError() throws Exception {

        webDriver.get(applicationContextUrl + "/test/error/dataAccessError");
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        assertThat(webDriver.getTitle(), is("Data Access Error!"));
        assertThat(webDriver.findElement(By.tagName("h1")).getText(), is(
                "Data Access Error!"));
        assertThat(webDriver.findElement(By.className("error")).getText(), is(
                "[e.xx.fw.9002] Data Access error!"));
    }

    /**
     * Invalid CSRF token error page test.
     * @throws Exception
     */
    @Test
    public void testInvalidCsrfTokenError() throws Exception {

        webDriver.get(applicationContextUrl
                + "/test/error/invalidCsrfTokenError");
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        assertThat(webDriver.getTitle(), is("CSRF Error!"));
        assertThat(webDriver.findElement(By.tagName("h1")).getText(), is(
                "CSRF Error! Invalid CSRF Token!"));
        assertThat(webDriver.findElement(By.className("error")).getText(), is(
                "[e.xx.fw.7002] CSRF attack detected!"));
    }

    /**
     * CSRF token missing error page test.
     * @throws Exception
     */
    @Test
    public void testMissingCsrfTokenError() throws Exception {

        webDriver.get(applicationContextUrl
                + "/test/error/missingCsrfTokenError");
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        assertThat(webDriver.getTitle(), is("CSRF Error!"));
        assertThat(webDriver.findElement(By.tagName("h1")).getText(), is(
                "CSRF Error! Missing CSRF Token!"));
        assertThat(webDriver.findElement(By.className("error")).getText(), is(
                "[e.xx.fw.7004] Missing CSRF detected!"));
    }

    /**
     * Testing error pages where resources do not exist
     * @throws Exception
     */
    @Test
    public void testResourceNotFoundError() throws Exception {

        webDriver.get(applicationContextUrl
                + "/test/error/resourceNotFoundError");
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        assertThat(webDriver.getTitle(), is("Resource Not Found Error!"));
        assertThat(webDriver.findElement(By.tagName("h1")).getText(), is(
                "Resource Not Found Error!"));
        assertThat(webDriver.findElement(By.className("error")).getText(), is(
                "[e.xx.fw.5001] Resource not found."));
    }

    /**
     * System error page test.
     * @throws Exception
     */
    @Test
    public void testSystemError() throws Exception {

        webDriver.get(applicationContextUrl + "/test/error/systemError");
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        assertThat(webDriver.getTitle(), is("System Error!"));
        assertThat(webDriver.findElement(By.tagName("h1")).getText(), is(
                "System Error!"));
        assertThat(webDriver.findElement(By.className("error")).getText(), is(
                "[e.xx.fw.9001] System error occurred!"));
    }

    /**
     * Double submission error page test.
     * @throws Exception
     */
    @Test
    public void testTransactionTokenError() throws Exception {

        webDriver.get(applicationContextUrl
                + "/test/error/transactionTokenError");
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        assertThat(webDriver.getTitle(), is("Transaction Token Error!"));
        assertThat(webDriver.findElement(By.tagName("h1")).getText(), is(
                "Transaction Token Error!"));
        assertThat(webDriver.findElement(By.className("error")).getText(), is(
                "[e.xx.fw.7001] Illegal screen flow detected!"));
    }

    /**
     * Unexpected exception page test.
     * @throws Exception
     */
    @Test
    public void testUnhandledSystemError() throws Exception {

        webDriver.get(applicationContextUrl + "/test/error/other");
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        assertThat(webDriver.getTitle(), is("Unhandled System Error!"));
        assertThat(webDriver.findElement(By.tagName("h1")).getText(), is(
                "Unhandled System Error!"));
        assertThat(webDriver.findElement(By.className("error")).getText(), is(
                "Unhandled system error occurred!"));
    }

}
