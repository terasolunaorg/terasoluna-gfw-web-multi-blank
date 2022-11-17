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

import jakarta.inject.Inject;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.terasoluna.gfw.common.codelist.JdbcCodeList;
import org.terasoluna.gfw.common.codelist.CodeList;

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
     * Load database setup during initialization.
     * Bean defined by xxxxxx.yyyyyy.zzzzzz.config.app.ProjectNameEnvConfig#dataSourceInitializer
     * @see xxxxxx.yyyyyy.zzzzzz.config.app.ProjectNameEnvConfig#dataSourceInitializer(DataSource)
     */
    @Inject
    DataSourceInitializer dataSourceInitializer;

    /**
     * Configure {@link JdbcTemplate} bean.
     * @param dataSource Bean defined by xxxxxx.yyyyyy.zzzzzz.config.app.ProjectNameEnvConfig#dataSource
     * @see xxxxxx.yyyyyy.zzzzzz.config.app.ProjectNameEnvConfig#dataSource()
     * @return Bean of configured {@link JdbcTemplate}
     */
    @Bean("jdbcTemplateForCodeList")
    public JdbcTemplate jdbcTemplateForCodeList(DataSource dataSource) {
        JdbcTemplate bean = new JdbcTemplate();
        bean.setDataSource(dataSource);
        bean.setFetchSize(fetchSize);
        return bean;
    }

    /**
     * Common processing of {@link JdbcCodeList}.
     * @param jdbcTemplateForCodeList Bean defined by #jdbcTemplateForCodeList
     * @see #jdbcTemplateForCodeList(DataSource)
     * @return Bean of configured {@link JdbcCodeList}
     */
    private JdbcCodeList abstractJdbcCodeList(JdbcTemplate jdbcTemplateForCodeList) {
        JdbcCodeList bean = new JdbcCodeList();
        bean.setJdbcTemplate(jdbcTemplateForCodeList);
        return bean;
    }

//    Example for usage of AbstractJdbcCodeList
//    /**
//     * Example for usage of {@link AbstractJdbcCodeList}.
//     * @param jdbcTemplateForCodeList Bean defined by #jdbcTemplateForCodeList
//     * @see #jdbcTemplateForCodeList(DataSource)
//     * @return Bean of configured {@link JdbcCodeList}
//     */
//    @Bean("CL_SAMPLE")
//    public CodeList CL_SAMPLE(JdbcTemplate jdbcTemplateForCodeList) {
//        JdbcCodeList bean = abstractJdbcCodeList(jdbcTemplateForCodeList);
//        bean.setJdbcTemplate(jdbcTemplateForCodeList);
//        bean.setQuerySql("SELECT code, code_name FROM t_sample_codes ORDER BY code");
//        bean.setValueColumn("code");
//        bean.setLabelColumn("code_name");
//        return bean;
//    }
}