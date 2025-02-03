package xxxxxx.yyyyyy.zzzzzz.selenium.webdrivers;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import io.github.bonigarcia.wdm.WebDriverManager;

public class FirefoxDriverFactoryBean extends
                                      HeadlessWebDriverManagerFactoryBean<FirefoxDriver> {

    @Override
    protected WebDriverManager getWebDriverManager() {
        return WebDriverManager.firefoxdriver();
    }

    @Override
    protected FirefoxDriver createWebDriver() {

        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.startup.homepage_override.mstone",
                "ignore");
        profile.setPreference("network.proxy.type", 0);
        FirefoxOptions options = new FirefoxOptions().setProfile(profile);

        if (super.headless) {
            options.addArguments("--headless");
        }

        return new FirefoxDriver(options);
    }
}