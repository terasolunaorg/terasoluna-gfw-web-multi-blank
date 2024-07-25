package xxxxxx.yyyyyy.zzzzzz.selenium;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

/**
 * Class that provides a (logic for WebDriver) browser operation.
 */
public class WebDriverOperations {
    protected final WebDriver webDriver;

    protected Duration defaultTimeoutSecForImplicitlyWait;

    public WebDriverOperations(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    /**
     * Set the default timeout value of the waiting process to find the element.
     * @param defaultTimeoutSecForImplicitlyWait The default timeout value of the waiting process to
     *        find the element (s)
     */
    public void setDefaultTimeoutForImplicitlyWait(Duration defaultTimeoutSecForImplicitlyWait) {
        this.defaultTimeoutSecForImplicitlyWait = defaultTimeoutSecForImplicitlyWait;
    }

    /**
     * Check the specified element exists.
     * @param by Identifier to look for elements
     * @return And returns true if the specified element is present.
     */
    public boolean exists(By by) {
        webDriver.findElement(By.tagName("body"));
        setTimeoutForImplicitlyWait(Duration.ZERO);
        boolean existsElement = true;
        try {
            webDriver.findElement(by).getText();
        } catch (NoSuchElementException e) {
            existsElement = false;
        } finally {
            setDefaultTimeoutForImplicitlyWait();
        }
        return existsElement;
    }

    /**
     * Set to the default value of the timeout value waiting process to find the element.
     */
    public void setDefaultTimeoutForImplicitlyWait() {
        setTimeoutForImplicitlyWait(this.defaultTimeoutSecForImplicitlyWait);
    }

    /**
     * Set the time-out value of the waiting process to find the element.
     */
    public void setTimeoutForImplicitlyWait(Duration duration) {
        webDriver.manage().timeouts().implicitlyWait(duration);
    }

}
