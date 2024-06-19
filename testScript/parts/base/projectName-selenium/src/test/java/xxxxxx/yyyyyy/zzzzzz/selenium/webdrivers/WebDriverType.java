package xxxxxx.yyyyyy.zzzzzz.selenium.webdrivers;

import org.openqa.selenium.WebDriver;

/**
 * Supported {@link WebDriver} types.
 */
public enum WebDriverType {
    FIREFOX, CHROME, EDGE;

    /**
     * The default {@link WebDriver} type.
     * @return {@link #FIREFOX}
     */
    public static WebDriverType DEFAULT() {
        return FIREFOX;
    }
}
