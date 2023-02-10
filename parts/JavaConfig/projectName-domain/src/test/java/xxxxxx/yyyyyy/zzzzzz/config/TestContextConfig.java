package xxxxxx.yyyyyy.zzzzzz.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.terasoluna.gfw.common.exception.ExceptionLogger;
import org.terasoluna.gfw.common.exception.SimpleMappingExceptionCodeResolver;

/**
 * Bean definition to TestContext configure .
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackageClasses = TestContextConfig.class)
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
     * @param exceptionCodeResolver Bean defined by ApplicationContext#exceptionCodeResolver
     * @see xxxxxx.yyyyyy.zzzzzz.config.app.ApplicationContext#exceptionCodeResolver()
     * @return Bean of configured {@link ExceptionLogger}
     */
    @Bean("exceptionLogger")
    public static ExceptionLogger exceptionLogger(SimpleMappingExceptionCodeResolver simpleMappingExceptionCodeResolver) {
        ExceptionLogger bean = new ExceptionLogger();
        bean.setExceptionCodeResolver(simpleMappingExceptionCodeResolver);
        return bean;
    }
    
    /**
     * Configure {@link JdbcTemplate} bean.
     * @param dataSource Bean defined by EnvConfig#dataSource
     * @see xxxxxx.yyyyyy.zzzzzz.config.app.ProjectNameEnv#dataSource()
     * @return Bean of configured {@link JdbcTemplate}
     */
    @Bean("jdbcTemplate")
    public JdbcTemplate jdbcTemplateForCodeList(DataSource dataSource) {
        JdbcTemplate bean = new JdbcTemplate();
        bean.setDataSource(dataSource);
        return bean;
    }
}
