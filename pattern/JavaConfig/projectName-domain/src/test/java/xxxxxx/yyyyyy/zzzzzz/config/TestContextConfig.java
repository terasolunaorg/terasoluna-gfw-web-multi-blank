/*
 * Copyright(c) 2013 NTT DATA Corporation. Copyright(c) 2013 NTT Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
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
