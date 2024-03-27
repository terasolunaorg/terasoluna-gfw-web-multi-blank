package xxxxxx.yyyyyy.zzzzzz.selenium.webdrivers;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import io.github.bonigarcia.wdm.WebDriverManager;

public abstract class WebDriverManagerFactoryBean<T extends WebDriver>
                                                 implements FactoryBean<T>,
                                                 InitializingBean {

    private String propertyFileLocation;

    public void setPropertyFileLocation(String propertyFileLocation) {
        this.propertyFileLocation = propertyFileLocation;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (propertyFileLocation != null) {
            WebDriverManager.firefoxdriver().config().setProperties(
                    propertyFileLocation);
        }
    }

}
