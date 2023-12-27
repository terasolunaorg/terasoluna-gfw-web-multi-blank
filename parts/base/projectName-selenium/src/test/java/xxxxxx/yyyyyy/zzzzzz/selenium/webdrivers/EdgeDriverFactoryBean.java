package xxxxxx.yyyyyy.zzzzzz.selenium.webdrivers;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class EdgeDriverFactoryBean extends
                                   WebDriverManagerFactoryBean<EdgeDriver> {

    @Override
    public EdgeDriver getObject() {
        if (System.getenv("webdriver.edge.driver") == null) {
            WebDriverManager.edgedriver().setup();
        }
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--remote-allow-origins=*");
        return new EdgeDriver(options);
    }

    @Override
    public Class<?> getObjectType() {
        return EdgeDriver.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

}
