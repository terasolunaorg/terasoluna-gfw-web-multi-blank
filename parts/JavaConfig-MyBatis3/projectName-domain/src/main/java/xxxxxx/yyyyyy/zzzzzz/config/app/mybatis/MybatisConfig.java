package xxxxxx.yyyyyy.zzzzzz.config.app.mybatis;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.apache.ibatis.type.TypeHandlerRegistry;

/**
 * Mybatis config.
 */
public class MybatisConfig {

    /**
     * Settings Mybatis config.
     * @return Configured {@link Configuration}
     */
    public static Configuration configuration() {
        Configuration configuration = new Configuration();
        setSettings(configuration);
        setTypeAliases(configuration.getTypeAliasRegistry());
        setTypeHandlers(configuration.getTypeHandlerRegistry());
        return configuration;
    }

    /**
     * Settings MyBatis behaves.
     * @param configuration Accepted at configuration
     */
    private static void setSettings(Configuration configuration) {
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setLazyLoadingEnabled(true);
        configuration.setDefaultFetchSize(100);
        // configuration.setDefaultExecutorType(ExecutorType.REUSE);
        // configuration.setJdbcTypeForNull(JdbcType.NULL);
        // configuration.setLocalCacheScope(LocalCacheScope.STATEMENT);
    }

    /**
     * Settings type aliases.
     * @param typeAliasRegistry Accepted at configuration
     */
    private static void setTypeAliases(TypeAliasRegistry typeAliasRegistry) {
        typeAliasRegistry.registerAliases("xxxxxx.yyyyyy.zzzzzz.domain.model");
        typeAliasRegistry.registerAliases("xxxxxx.yyyyyy.zzzzzz.domain.repository");
        // typeAliasRegistry.registerAliases("xxxxxx.yyyyyy.zzzzzz.infra.mybatis.typehandler");
    }

    /**
     * Settings type handlers.
     * @param typeHandlerRegistry Accepted at configuration
     */
    private static void setTypeHandlers(TypeHandlerRegistry typeHandlerRegistry) {
        // typeHandlerRegistry.register("xxxxxx.yyyyyy.zzzzzz.infra.mybatis.typehandler");
    }
}
