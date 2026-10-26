package xxxxxx.yyyyyy.zzzzzz.selenium;

import java.io.File;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestWatcher;
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

    @RegisterExtension
    FunctionTestSupportExtension extension = new FunctionTestSupportExtension(this);

    /**
     * Extension that saves the evidence according to the test result.
     * <p>
     * The evidence saving directory is set up in {@link BeforeEachCallback} because it is invoked
     * before all {@link BeforeEach} methods, so that evidence can be saved even if the setup of
     * the WebDriver failed.
     * </p>
     */
    private static class FunctionTestSupportExtension
            implements BeforeEachCallback, TestWatcher {

        private final FunctionTestSupport testSupport;

        FunctionTestSupportExtension(FunctionTestSupport testSupport) {
            this.testSupport = testSupport;
        }

        @Override
        public void beforeEach(ExtensionContext context) {
            testSupport.setUpEvidence(context.getRequiredTestMethod().getName());
        }

        @Override
        public void testSuccessful(ExtensionContext context) {
            testSupport.succeededEvidence();
        }

        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            testSupport.failedEvidence();
        }
    }

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

    private ScreenCapture screenCapture;

    private PageSource pageSource;

    private boolean useSetupDefaultWebDriver = true;

    protected Duration defaultTimeoutSecForImplicitlyWait;

    private final String simplePackageName;

    protected FunctionTestSupport() {
        this.simplePackageName = this.getClass().getPackage().getName().replaceAll(".*\\.", "");
    }

    @Value("${selenium.defaultTimeoutSecForImplicitlyWait:5}")
    public void setDefaultTimeoutSecForImplicitlyWait(long defaultTimeoutSecForImplicitlyWait) {
        this.defaultTimeoutSecForImplicitlyWait =
                Duration.ofSeconds(defaultTimeoutSecForImplicitlyWait);
    }

    @Value("${selenium.enableCapture:false}")
    public void setEnableCapture(boolean enableCapture) {
        this.screenCapture = new ScreenCapture(enableCapture);
    }

    @Value("${selenium.enablePageSource:false}")
    public void setEnablePageSource(boolean enablePageSource) {
        this.pageSource = new PageSource(enablePageSource);
    }

    private void setUpEvidence(String testName) {
        String testCaseName = testName.replaceAll("^test", "");

        evidenceSavingDirectory = new File(
                String.format("%s/%s/%s", evidenceBaseDirectory, simplePackageName, testCaseName));

        logger.debug("evidenceSavingDirectory is " + evidenceSavingDirectory.getAbsolutePath());

        screenCapture.setUp(evidenceSavingDirectory);
        pageSource.setUp(evidenceSavingDirectory);
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

    /**
     * Save the evidence when the test succeeded.
     * <p>
     * Evidence is saved only if it is enabled by {@code selenium.enableCapture} or
     * {@code selenium.enablePageSource}.
     * </p>
     */
    protected void succeededEvidence() {
        String subTitle = "succeeded";
        for (WebDriver webDriver : webDrivers) {
            try {
                screenCapture.save(webDriver, subTitle);
            } catch (Throwable t) {
                logger.error("failed screen capture.", t);
            }
            try {
                pageSource.save(webDriver, subTitle);
            } catch (Throwable t) {
                logger.error("failed page source.", t);
            }
        }
    }

    /**
     * Save the evidence when the test failed.
     * <p>
     * Evidence is always saved regardless of the setting.
     * </p>
     */
    protected void failedEvidence() {
        String subTitle = "failed";
        for (WebDriver webDriver : webDrivers) {
            try {
                screenCapture.saveForced(webDriver, subTitle);
            } catch (Throwable t) {
                logger.error("failed screen capture.", t);
            }
            try {
                pageSource.saveForced(webDriver, subTitle);
            } catch (Throwable t) {
                logger.error("failed page source.", t);
            }
        }
    }
}
