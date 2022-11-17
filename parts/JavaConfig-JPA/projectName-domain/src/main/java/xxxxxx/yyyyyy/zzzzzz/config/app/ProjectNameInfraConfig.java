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
package xxxxxx.yyyyyy.zzzzzz.config.app;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/**
 * Bean definitions for infrastructure layer.
 */
@Configuration
@EnableJpaRepositories("xxxxxx.yyyyyy.zzzzzz.domain.repository")
public class ProjectNameInfraConfig {

    /**
     * Database property.
     */
    @Value("${database}")
    Database database;

    /**
     * Configure {@link HibernateJpaVendorAdapter} bean.
     * @return Bean of configured {@link HibernateJpaVendorAdapter}
     */
    @Bean("jpaVendorAdapter")
    public HibernateJpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter bean = new HibernateJpaVendorAdapter();
        bean.setDatabase(database);
        return bean;
    }

    /**
     * Configure {@link LocalContainerEntityManagerFactoryBean} bean.
     * @param dataSource DataSource
     * @param jpaVendorAdapter HibernateJpaVendorAdapter
     * @return Bean of configured {@link LocalContainerEntityManagerFactoryBean}
     */
    @Bean("entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, HibernateJpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setPackagesToScan("xxxxxx.yyyyyy.zzzzzz.domain.model");
        bean.setDataSource(dataSource);
        bean.setJpaVendorAdapter(jpaVendorAdapter);
        bean.setJpaPropertyMap(jpaPropertyMap());
        return bean;
    }
    /**
     * Configure {@link LocalContainerEntityManagerFactoryBean}.JpaPropertyMap.
     * @return configured JpaPropertyMap
     */
    private Map<String, ?> jpaPropertyMap() {
        Map<String, Object> jpaPropertyMap = new HashMap<>();
        jpaPropertyMap.put("hibernate.connection.charSet", "UTF-8");
        jpaPropertyMap.put("hibernate.format_sql", false);
        jpaPropertyMap.put("hibernate.use_sql_comments", true);
        jpaPropertyMap.put("hibernate.jdbc.batch_size", 30);
        jpaPropertyMap.put("hibernate.jdbc.fetch_size", 100);
        return jpaPropertyMap;
    }
}