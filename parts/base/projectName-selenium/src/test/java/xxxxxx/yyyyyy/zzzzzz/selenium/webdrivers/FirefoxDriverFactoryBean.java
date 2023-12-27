package xxxxxx.yyyyyy.zzzzzz.selenium.webdrivers;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FirefoxDriverFactoryBean extends
                                      WebDriverManagerFactoryBean<FirefoxDriver> {

    @Override
    public FirefoxDriver getObject() {
        if (System.getenv("webdriver.gecko.driver") == null) {
            WebDriverManager.firefoxdriver().setup();
        }

        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.startup.homepage_override.mstone",
                "ignore");
        profile.setPreference("network.proxy.type", 0);
        FirefoxOptions options = new FirefoxOptions().setProfile(profile);
        return new FirefoxDriver(options);
    }

    @Override
    public Class<?> getObjectType() {
        return FirefoxDriver.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

}
