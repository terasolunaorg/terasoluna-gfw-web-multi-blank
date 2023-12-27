package xxxxxx.yyyyyy.zzzzzz.config;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;

import xxxxxx.yyyyyy.zzzzzz.selenium.webdrivers.ChromeDriverFactoryBean;
import xxxxxx.yyyyyy.zzzzzz.selenium.webdrivers.EdgeDriverFactoryBean;
import xxxxxx.yyyyyy.zzzzzz.selenium.webdrivers.FirefoxDriverFactoryBean;

/**
 * Bean definition to SeleniumContext configure .
 */
@Configuration
public class SeleniumContextConfig {

    /**
     * Configure {@link PropertySourcesPlaceholderConfigurer} bean.
     * @param properties Path where the property file is located
     * @return Bean of configured {@link PropertySourcesPlaceholderConfigurer}
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(
            @Value("classpath*:META-INF/spring/*.properties") Resource... properties) {
        PropertySourcesPlaceholderConfigurer bean = new PropertySourcesPlaceholderConfigurer();
        bean.setLocations(properties);
        return bean;
    }

    /**
     * Configure {@link WebDriver} bean.
     * @return Bean of configured {@link FirefoxDriverFactoryBean}
     */
    @Bean("webDriver")
    @Scope("prototype")
    @Profile({ "firefox", "default" })
    public FirefoxDriverFactoryBean firefoxDriverFactoryBean() {
        return new FirefoxDriverFactoryBean();
    }

    /**
     * Configure {@link WebDriver} bean.
     * @return Bean of configured {@link ChromeDriverFactoryBean}
     */
    @Bean("webDriver")
    @Scope("prototype")
    @Profile("chrome")
    public ChromeDriverFactoryBean chromeDriverFactoryBean() {
        return new ChromeDriverFactoryBean();
    }

    /**
     * Configure {@link WebDriver} bean.
     * @return Bean of configured {@link EdgeDriverFactoryBean}
     */
    @Bean("webDriver")
    @Scope("prototype")
    @Profile("edge")
    public EdgeDriverFactoryBean edgeDriverFactoryBean() {
        return new EdgeDriverFactoryBean();
    }

}
