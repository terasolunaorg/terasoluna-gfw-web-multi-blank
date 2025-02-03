package xxxxxx.yyyyyy.zzzzzz.selenium.webdrivers;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;
public abstract class HeadlessWebDriverManagerFactoryBean<T extends WebDriver>
                                                         extends
                                                         WebDriverManagerFactoryBean<T>
                                                         implements
                                                         InitializingBean {

    protected boolean headless = true;

    public void setHeadless(boolean headless) {
        this.headless = headless;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // Prioritize startup arguments
        String headlessProperty = System.getProperty("selenium.headless");
        if (StringUtils.hasLength(headlessProperty)) {
            this.headless = Boolean.valueOf(headlessProperty);
        }
    }
}
