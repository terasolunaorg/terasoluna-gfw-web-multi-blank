package xxxxxx.yyyyyy.zzzzzz.config.app;

import jakarta.inject.Inject;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Bean definition regarding CodeLists.
 */
@Configuration
public class ProjectNameCodeListConfig {

    /**
     * JDBC fetchSize property.
     */
    @Value("${codelist.jdbc.fetchSize:1000}")
    private Integer fetchSize;

    /**
     * Bean of DataSource.
     */
    @Inject
    private DataSource dataSource;

    /**
     * Configure {@link JdbcTemplate} bean.
     * @return Bean of configured {@link JdbcTemplate}
     */
    @Bean("jdbcTemplateForCodeList")
    public JdbcTemplate jdbcTemplateForCodeList() {
        JdbcTemplate bean = new JdbcTemplate();
        bean.setDataSource(dataSource);
        bean.setFetchSize(fetchSize);
        return bean;
    }

    // Example for usage of AbstractJdbcCodeList

//  /**
//   * Common processing of {@link JdbcCodeList}.
//   * @return Bean of configured {@link JdbcCodeList}
//   */
//  private JdbcCodeList abstractJdbcCodeList() {
//      JdbcCodeList bean = new JdbcCodeList();
//      bean.setJdbcTemplate(jdbcTemplateForCodeList());
//      return bean;
//  }

//  /**
//  * Example for usage of {@link AbstractJdbcCodeList}.
//  * @return Bean of configured {@link JdbcCodeList}
//  */
//  @Bean("CL_SAMPLE")
//  public JdbcCodeList clSample() {
//      JdbcCodeList jdbcCodeList = abstractJdbcCodeList();
//      jdbcCodeList.setQuerySql(
//              "SELECT code, code_name FROM t_sample_codes ORDER BY code");
//      jdbcCodeList.setValueColumn("code");
//      jdbcCodeList.setLabelColumn("code_name");
//      return jdbcCodeList;
//  }

}