package xxxxxx.yyyyyy.zzzzzz.config.app;

import java.time.Duration;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
/* REMOVE THIS LINE IF YOU USE MyBatis3
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
REMOVE THIS LINE IF YOU USE MyBatis3 */
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
/* REMOVE THIS LINE IF YOU USE JPA
import org.springframework.orm.jpa.JpaTransactionManager;
REMOVE THIS LINE IF YOU USE JPA */
import org.springframework.transaction.TransactionManager;
import org.terasoluna.gfw.common.time.ClockFactory;
import org.terasoluna.gfw.common.time.DefaultClockFactory;
/* REMOVE THIS LINE IF YOU USE JPA
import jakarta.persistence.EntityManagerFactory;
REMOVE THIS LINE IF YOU USE JPA */

/**
 * Define settings for the environment.
 */
@Configuration
public class ProjectNameEnvConfig {

    /**
     * DataSource.driverClassName property.
     */
    @Value("${database.driverClassName}")
    private String driverClassName;

    /**
     * DataSource.url property.
     */
    @Value("${database.url}")
    private String url;

    /**
     * DataSource.username property.
     */
    @Value("${database.username}")
    private String username;

    /**
     * DataSource.password property.
     */
    @Value("${database.password}")
    private String password;

    /**
     * DataSource.maxTotal property.
     */
    @Value("${cp.maxActive}")
    private Integer maxActive;

    /**
     * DataSource.maxIdle property.
     */
    @Value("${cp.maxIdle}")
    private Integer maxIdle;

    /**
     * DataSource.minIdle property.
     */
    @Value("${cp.minIdle}")
    private Integer minIdle;

    /**
     * DataSource.maxWaitMillis property.
     */
    @Value("${cp.maxWait}")
    private Integer maxWait;

    /**
     * Property databaseName.
     */
    @Value("${database}")
    private String database;

    /**
     * Configure {@link ClockFactory}.
     * @return Bean of configured {@link DefaultClockFactory}
     */
    @Bean("dateFactory")
    public ClockFactory dateFactory() {
        return new DefaultClockFactory();
    }

    /**
     * Configure {@link DataSource} bean.
     * @return Bean of configured {@link BasicDataSource}
     */
    @Bean(name = "dataSource", destroyMethod = "close")
    public DataSource dataSource() {
        BasicDataSource bean = new BasicDataSource();
        bean.setDriverClassName(driverClassName);
        bean.setUrl(url);
        bean.setUsername(username);
        bean.setPassword(password);
        bean.setDefaultAutoCommit(false);
        bean.setMaxTotal(maxActive);
        bean.setMaxIdle(maxIdle);
        bean.setMinIdle(minIdle);
        bean.setMaxWait(Duration.ofMillis(maxWait));
        return bean;
    }

    /**
     * Configuration to set up database during initialization.
     * @return Bean of configured {@link DataSourceInitializer}
     */
    @Bean
    public DataSourceInitializer dataSourceInitializer() {
        DataSourceInitializer bean = new DataSourceInitializer();
        bean.setDataSource(dataSource());

        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScript(new ClassPathResource("/database/" + database + "-schema.sql"));
        databasePopulator
                .addScript(new ClassPathResource("/database/" + database + "-dataload.sql"));
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.setIgnoreFailedDrops(true);
        bean.setDatabasePopulator(databasePopulator);
        return bean;
    }

    // @formatter:off
    /* REMOVE THIS LINE IF YOU USE JPA
    /**
     * Configure {@link TransactionManager} bean for use with JPA.
     * @param entityManagerFactory EntityManager used within a transaction
     * @return Bean of configured {@link JpaTransactionManager}
     *REMOVE THIS COMMENT IF YOU USE JPA/
    @Bean("transactionManager")
    public TransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager bean = new JpaTransactionManager();
        bean.setEntityManagerFactory(entityManagerFactory);
        return bean;
    }
    REMOVE THIS LINE IF YOU USE JPA */

    /* REMOVE THIS LINE IF YOU USE MyBatis3
    /**
     * Configure {@link TransactionManager} bean.
     * @return Bean of configured {@link DataSourceTransactionManager}
     *REMOVE THIS COMMENT IF YOU USE MyBatis3/
    @Bean("transactionManager")
    public TransactionManager transactionManager() {
        DataSourceTransactionManager bean = new DataSourceTransactionManager();
        bean.setDataSource(dataSource());
        bean.setRollbackOnCommitFailure(true);
        return bean;
    }
    REMOVE THIS LINE IF YOU USE MyBatis3 */
    // @formatter:on

}
