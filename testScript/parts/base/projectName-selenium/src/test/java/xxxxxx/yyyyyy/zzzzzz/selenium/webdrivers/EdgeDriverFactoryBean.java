package xxxxxx.yyyyyy.zzzzzz.selenium.webdrivers;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class EdgeDriverFactoryBean extends
                                   HeadlessWebDriverManagerFactoryBean<EdgeDriver> {

    @Override
    protected WebDriverManager getWebDriverManager() {
        return WebDriverManager.edgedriver();
    }

    @Override
    protected EdgeDriver createWebDriver() {

        EdgeOptions options = new EdgeOptions();
        options.addArguments("--remote-allow-origins=*");

        if (super.headless) {
            options.addArguments("--headless=new");
        }

        return new EdgeDriver(options);
    }
}
