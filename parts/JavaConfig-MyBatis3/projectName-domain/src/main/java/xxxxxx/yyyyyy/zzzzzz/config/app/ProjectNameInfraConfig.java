package xxxxxx.yyyyyy.zzzzzz.config.app;

import javax.sql.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import xxxxxx.yyyyyy.zzzzzz.config.app.mybatis.MybatisConfig;

/**
 * Bean definitions for infrastructure layer.
 */
@Configuration
@MapperScan("xxxxxx.yyyyyy.zzzzzz.domain.repository")
@Import({ProjectNameEnvConfig.class})
public class ProjectNameInfraConfig {

    /**
     * Configure {@link SqlSessionFactory} bean.
     * @param dataSource DataSource
     * @see xxxxxx.yyyyyy.zzzzzz.config.app.ProjectNameEnvConfig#dataSource()
     * @return Bean of configured {@link SqlSessionFactoryBean}
     */
    @Bean("sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setConfiguration(MybatisConfig.configuration());
        return bean;
    }
}
