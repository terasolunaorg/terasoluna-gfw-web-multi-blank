package xxxxxx.yyyyyy.zzzzzz.config.app;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.terasoluna.gfw.common.exception.ExceptionLogger;
import org.terasoluna.gfw.common.exception.ResultMessagesLoggingInterceptor;

/**
 * Bean definitions for domain layer.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"xxxxxx.yyyyyy.zzzzzz.domain"})
@Import({ ProjectNameInfraConfig.class,
    ProjectNameCodeListConfig.class })
public class ProjectNameDomainConfig {

    /**
     * Configure {@link ResultMessagesLoggingInterceptor} bean.
     * @param exceptionLogger Bean defined by ApplicationContextConfig#exceptionLogger
     * @see xxxxxx.yyyyyy.zzzzzz.config.app.ApplicationContextConfig#exceptionLogger(ExceptionCodeResolver)
     * @return Bean of configured {@link ResultMessagesLoggingInterceptor}
     */
    @Bean("resultMessagesLoggingInterceptor")
    public ResultMessagesLoggingInterceptor resultMessagesLoggingInterceptor(ExceptionLogger exceptionLogger) {
        ResultMessagesLoggingInterceptor bean = new ResultMessagesLoggingInterceptor();
        bean.setExceptionLogger(exceptionLogger);
        return bean;
    }

    /**
     * Configure messages logging AOP advisor.
     * @param resultMessagesLoggingInterceptor Bean defined by #resultMessagesLoggingInterceptor
     * @see #resultMessagesLoggingInterceptor(ExceptionLogger)
     * @return Advisor configured for PointCut
     */
    @Bean
    public Advisor resultMessagesLoggingInterceptorAdvisor(ResultMessagesLoggingInterceptor resultMessagesLoggingInterceptor) {
       AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
       pointcut.setExpression("@within(org.springframework.stereotype.Service)");
       return new DefaultPointcutAdvisor(pointcut, resultMessagesLoggingInterceptor);
    }
}