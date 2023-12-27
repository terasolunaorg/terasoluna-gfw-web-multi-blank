package xxxxxx.yyyyyy.zzzzzz.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.terasoluna.gfw.common.exception.ExceptionLogger;

import xxxxxx.yyyyyy.zzzzzz.config.app.ProjectNameDomainConfig;

/**
 * Bean definition to TestContext configure .
 */
@Configuration
@EnableAspectJAutoProxy
@Import({ ProjectNameDomainConfig.class })
public class TestContextConfig {

    /**
     * Configure PropertySourcesPlaceholderConfigurer bean.
     * @param resources Path where the property file is located
     * @return Bean of configured PropertySourcesPlaceholderConfigurer
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(
            @Value("classpath*:/META-INF/spring/*.properties") Resource... resources) {
        PropertySourcesPlaceholderConfigurer placehodler = new PropertySourcesPlaceholderConfigurer();
        placehodler.setLocations(resources);
        return placehodler;
    }

    /**
     * Configure {@link ExceptionLogger} bean.
     * @return Bean of configured {@link ExceptionLogger}
     */
    @Bean("exceptionLogger")
    public static ExceptionLogger exceptionLogger() {
        return new ExceptionLogger();
    }

    /**
     * Configure {@link JdbcTemplate} bean.
     * @param dataSource Bean defined by EnvConfig#dataSource
     * @see xxxxxx.yyyyyy.zzzzzz.config.app.ProjectNameEnvConfig#dataSource()
     * @return Bean of configured {@link JdbcTemplate}
     */
    @Bean("jdbcTemplate")
    public JdbcTemplate jdbcTemplateForCodeList(DataSource dataSource) {
        JdbcTemplate bean = new JdbcTemplate();
        bean.setDataSource(dataSource);
        return bean;
    }
}
