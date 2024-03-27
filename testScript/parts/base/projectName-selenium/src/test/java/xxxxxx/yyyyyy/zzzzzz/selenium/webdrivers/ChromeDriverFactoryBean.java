package xxxxxx.yyyyyy.zzzzzz.selenium.webdrivers;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ChromeDriverFactoryBean extends
                                     WebDriverManagerFactoryBean<ChromeDriver> {

    @Override
    public ChromeDriver getObject() {
        if (System.getenv("webdriver.chrome.driver") == null) {
            WebDriverManager.chromedriver().setup();
        }
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        return new ChromeDriver(options);
    }

    @Override
    public Class<?> getObjectType() {
        return ChromeDriver.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

}
