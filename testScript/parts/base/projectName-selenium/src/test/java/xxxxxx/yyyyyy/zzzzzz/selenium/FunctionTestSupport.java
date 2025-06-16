package xxxxxx.yyyyyy.zzzzzz.selenium;

import java.io.File;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ApplicationObjectSupport;

import xxxxxx.yyyyyy.zzzzzz.selenium.webdrivers.WebDriverType;

public class FunctionTestSupport extends ApplicationObjectSupport {

    private static final Logger logger = LoggerFactory.getLogger(FunctionTestSupport.class);

    protected static WebDriver webDriver;

    private static final Set<WebDriver> webDrivers = new HashSet<WebDriver>();

    protected static WebDriverType driverType;

    @Value("${selenium.serverUrl}")
    protected String serverUrl;

    @Value("${selenium.contextName}")
    protected String contextName;

    @Value("${selenium.applicationContextUrl}")
    protected String applicationContextUrl;

    @Value("${selenium.evidenceBaseDirectory}")
    protected String evidenceBaseDirectory;

    protected WebDriverOperations webDriverOperations;

    protected WebDriverWait webDriverWait;

    protected File evidenceSavingDirectory;

    private boolean useSetupDefaultWebDriver = true;

    protected Duration defaultTimeoutSecForImplicitlyWait;

    @Value("${selenium.defaultTimeoutSecForImplicitlyWait:5}")
    public void setDefaultTimeoutSecForImplicitlyWait(long defaultTimeoutSecForImplicitlyWait) {
        this.defaultTimeoutSecForImplicitlyWait =
                Duration.ofSeconds(defaultTimeoutSecForImplicitlyWait);
    }

    @AfterAll
    public final static void tearDownWebDrivers() {
        quitWebDrivers();
        webDriver = null;
    }

    @BeforeEach
    public final void setUpDefaultWebDriver() {
        if (!useSetupDefaultWebDriver) {
            return;
        }
        bootDefaultWebDriver();
    }

    @BeforeEach
    public final void setUpWebDriverType() {
        if (driverType != null) {
            return;
        }
        for (String activeProfile : getApplicationContext().getEnvironment().getActiveProfiles()) {
            for (WebDriverType type : WebDriverType.values()) {
                if (type.toString().equalsIgnoreCase(activeProfile)) {
                    driverType = type;
                    return;
                }
            }
        }
        driverType = WebDriverType.DEFAULT();
    }

    @BeforeEach
    public final void logUserAgent() {
        if (webDriver instanceof RemoteWebDriver remoteWebDriver) {
            Object agent = remoteWebDriver.executeScript("return navigator.userAgent");
            if (agent != null) {
                logger.info("userAgent:" + agent.toString());
            }
        }
    }

    protected void bindWebDriver(WebDriver webDriver) {
        webDrivers.add(webDriver);
    }

    protected void unbindWebDriver(WebDriver webDriver) {
        webDrivers.remove(webDriver);
    }

    protected void bootDefaultWebDriver() {
        if (webDriver == null) {
            webDriver = newWebDriver();
        }
        webDriver.manage().timeouts().implicitlyWait(this.defaultTimeoutSecForImplicitlyWait);
        webDriver.get(getPackageRootUrl());

        this.webDriverOperations = new WebDriverOperations(webDriver);
        this.webDriverOperations
                .setDefaultTimeoutForImplicitlyWait(this.defaultTimeoutSecForImplicitlyWait);
        this.webDriverWait = new WebDriverWait(webDriver, this.defaultTimeoutSecForImplicitlyWait);
    }

    private WebDriver newWebDriver() {
        WebDriver webDriver = getApplicationContext().getBean(WebDriver.class);
        webDrivers.add(webDriver);
        return webDriver;
    }

    protected void quitDefaultWebDriver() {
        if (webDriver != null) {
            try {
                webDriver.quit();
            } finally {
                webDriver = null;
            }
        }
    }

    protected WebDriver getDefaultWebDriver() {
        return webDriver;
    }

    protected String getPackageRootUrl() {
        return applicationContextUrl + "/";
    }

    protected void disableSetupDefaultWebDriver() {
        this.useSetupDefaultWebDriver = false;
    }

    protected void enableSetupDefaultWebDriver() {
        this.useSetupDefaultWebDriver = true;
    }

    private static void quitWebDrivers() {
        for (WebDriver webDriver : webDrivers) {
            try {
                webDriver.quit();
            } catch (Throwable t) {
                logger.error("failed quit.", t);
            }
        }
        webDrivers.clear();
    }
}
