package xxxxxx.yyyyyy.zzzzzz.selenium.webdrivers;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.FactoryBean;
import io.github.bonigarcia.wdm.WebDriverManager;

public abstract class WebDriverManagerFactoryBean<T extends WebDriver> implements FactoryBean<T> {

    protected String propertyFileLocation;

    public void setPropertyFileLocation(String propertyFileLocation) {
        this.propertyFileLocation = propertyFileLocation;
    }

    protected abstract WebDriverManager getWebDriverManager();

    protected abstract T createWebDriver();

    @Override
    public T getObject() {
        if (System.getenv("webdriver.driver") == null) {
            WebDriverManager manager = getWebDriverManager();
            if (this.propertyFileLocation != null) {
                manager.config().setProperties(this.propertyFileLocation);
            }
            manager.setup();
        }

        return createWebDriver();
    }

    @Override
    public Class<?> getObjectType() {
        return WebDriver.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

}
