package xxxxxx.yyyyyy.zzzzzz.selenium.webdrivers;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ChromeDriverFactoryBean extends
                                     HeadlessWebDriverManagerFactoryBean<ChromeDriver> {

    @Override
    protected WebDriverManager getWebDriverManager() {
        return WebDriverManager.chromedriver();
    }

    @Override
    protected ChromeDriver createWebDriver() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        if (super.headless) {
            options.addArguments("--headless=new");
        }

        return new ChromeDriver(options);
    }
}
