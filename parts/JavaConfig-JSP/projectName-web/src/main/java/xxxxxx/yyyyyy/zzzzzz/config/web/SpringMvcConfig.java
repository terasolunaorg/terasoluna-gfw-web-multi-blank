package xxxxxx.yyyyyy.zzzzzz.config.web;

import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
/* REMOVE THIS LINE IF YOU USE JPA
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
REMOVE THIS LINE IF YOU USE JPA */
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.security.web.servlet.support.csrf.CsrfRequestDataValueProcessor;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.support.RequestDataValueProcessor;
import org.terasoluna.gfw.common.exception.ExceptionCodeResolver;
import org.terasoluna.gfw.common.exception.ExceptionLogger;
import org.terasoluna.gfw.web.codelist.CodeListInterceptor;
import org.terasoluna.gfw.web.exception.HandlerExceptionResolverLoggingInterceptor;
import org.terasoluna.gfw.web.exception.SystemExceptionResolver;
import org.terasoluna.gfw.web.logging.TraceLoggingInterceptor;
import org.terasoluna.gfw.web.mvc.support.CompositeRequestDataValueProcessor;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenInterceptor;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenRequestDataValueProcessor;

/**
 * Configure SpringMVC.
 */
@ComponentScan(basePackages = { "xxxxxx.yyyyyy.zzzzzz.app" })
@Configuration
public class SpringMvcConfig extends WebMvcConfigurationSupport {

    /**
     * Configure {@link PropertySourcesPlaceholderConfigurer} bean.
     * @param properties Property files to be read
     * @return Bean of configured {@link PropertySourcesPlaceholderConfigurer}
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(
            @Value("classpath*:/META-INF/spring/*.properties") Resource... properties) {
        PropertySourcesPlaceholderConfigurer bean = new PropertySourcesPlaceholderConfigurer();
        bean.setLocations(properties);
        return bean;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(pageableHandlerMethodArgumentResolver());
        argumentResolvers.add(authenticationPrincipalArgumentResolver());
    }
    /**
     * Configure {@link PageableHandlerMethodArgumentResolver} bean.
     * @return Bean of configured {@link PageableHandlerMethodArgumentResolver}
     */
    @Bean
    public PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver() {
        return new PageableHandlerMethodArgumentResolver();
    }
    /**
     * Configure {@link AuthenticationPrincipalArgumentResolver} bean.
     * @return Bean of configured {@link AuthenticationPrincipalArgumentResolver}
     */
    @Bean
    public AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver() {
        return new AuthenticationPrincipalArgumentResolver();
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
            .addResourceLocations("/resources/", "classpath:META-INF/resources/")
            .setCachePeriod(60 * 60);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        addInterceptor(registry, traceLoggingInterceptor());
        addInterceptor(registry, transactionTokenInterceptor());
        addInterceptor(registry, codeListInterceptor());
        /* REMOVE THIS LINE IF YOU USE JPA
        addWebRequestInterceptor(registry, openEntityManagerInViewInterceptor());
           REMOVE THIS LINE IF YOU USE JPA */
    }
    /**
     * Common processes used in #addInterceptors.
     * @param registry {@link InterceptorRegistry}
     * @param interceptor {@link HandlerInterceptor}
     */
    private void addInterceptor(InterceptorRegistry registry, HandlerInterceptor interceptor) {
        registry.addInterceptor(interceptor).addPathPatterns("/**").excludePathPatterns("/resources/**");
    }
    /**
     * Common processes used in #addInterceptors.
     * @param registry {@link InterceptorRegistry}
     * @param interceptor {@link WebRequestInterceptor}
     */
    private void addWebRequestInterceptor(InterceptorRegistry registry, WebRequestInterceptor interceptor) {
        registry.addWebRequestInterceptor(interceptor).addPathPatterns("/**").excludePathPatterns("/resources/**");
    }
    /**
     * Configure {@link TraceLoggingInterceptor} bean.
     * @return Bean of configured {@link TraceLoggingInterceptor}
     */
    @Bean
    public TraceLoggingInterceptor traceLoggingInterceptor() {
        return new TraceLoggingInterceptor();
    }
    /**
     * Configure {@link TransactionTokenInterceptor} bean.
     * @return Bean of configured {@link TransactionTokenInterceptor}
     */
    @Bean
    public TransactionTokenInterceptor transactionTokenInterceptor() {
        return new TransactionTokenInterceptor();
    }
    /**
     * Configure {@link CodeListInterceptor} bean.
     * @return Bean of configured {@link CodeListInterceptor}
     */
    @Bean
    public CodeListInterceptor codeListInterceptor() {
        CodeListInterceptor codeListInterceptor = new CodeListInterceptor();
        codeListInterceptor.setCodeListIdPattern(Pattern.compile("CL_.+"));
        return codeListInterceptor;
    }
    /* REMOVE THIS LINE IF YOU USE JPA
    /**
     * Configure {@link OpenEntityManagerInViewInterceptor} bean.
     * @return Bean of configured {@link OpenEntityManagerInViewInterceptor}
     *REMOVE THIS COMMENT IF YOU USE JPA/
    @Bean
    public OpenEntityManagerInViewInterceptor openEntityManagerInViewInterceptor() {
        return new OpenEntityManagerInViewInterceptor();
    }
    REMOVE THIS LINE IF YOU USE JPA */

    /**
     * {@inheritDoc}
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.beanName();
        registry.jsp("/WEB-INF/views/", ".jsp");
    }

    /**
     * Configure {@link RequestDataValueProcessor} bean.
     * @return Bean of configured {@link CompositeRequestDataValueProcessor}
     */
    @Bean("requestDataValueProcessor")
    public RequestDataValueProcessor requestDataValueProcessor() {
        return new CompositeRequestDataValueProcessor(
                csrfRequestDataValueProcessor(),
                transactionTokenRequestDataValueProcessor());
    }
    /**
     * Configure {@link CsrfRequestDataValueProcessor} bean.
     * @return Bean of configured {@link CsrfRequestDataValueProcessor}
     */
    @Bean
    public CsrfRequestDataValueProcessor csrfRequestDataValueProcessor() {
        return new CsrfRequestDataValueProcessor();
    }
    /**
     * Configure {@link TransactionTokenRequestDataValueProcessor} bean.
     * @return Bean of configured {@link TransactionTokenRequestDataValueProcessor}
     */
    @Bean
    public TransactionTokenRequestDataValueProcessor transactionTokenRequestDataValueProcessor() {
        return new TransactionTokenRequestDataValueProcessor();
    }

    /**
     * Configure {@link SystemExceptionResolver} bean.
     * @param exceptionCodeResolver Bean defined by ApplicationContext#exceptionCodeResolver
     * @see xxxxxx.yyyyyy.zzzzzz.config.app.ApplicationContext#exceptionCodeResolver()
     * @return Bean of configured {@link SystemExceptionResolver}
     */
    @Bean("systemExceptionResolver")
    public SystemExceptionResolver systemExceptionResolver(ExceptionCodeResolver exceptionCodeResolver) {
        SystemExceptionResolver bean = new SystemExceptionResolver();
        bean.setExceptionCodeResolver(exceptionCodeResolver);
        bean.setOrder(3);

        Properties exceptionMappings = new Properties();
        exceptionMappings.setProperty("ResourceNotFoundException", "common/error/resourceNotFoundError");
        exceptionMappings.setProperty("BusinessException", "common/error/businessError");
        exceptionMappings.setProperty("InvalidTransactionTokenException", "common/error/transactionTokenError");
        exceptionMappings.setProperty(".DataAccessException", "common/error/dataAccessError");
        bean.setExceptionMappings(exceptionMappings);

        Properties statusCodes = new Properties();
        statusCodes.setProperty("common/error/resourceNotFoundError", String.valueOf(HttpStatus.NOT_FOUND.value()));
        statusCodes.setProperty("common/error/businessError", String.valueOf(HttpStatus.CONFLICT.value()));
        statusCodes.setProperty("common/error/transactionTokenError", String.valueOf(HttpStatus.CONFLICT.value()));
        statusCodes.setProperty("common/error/dataAccessError", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        bean.setStatusCodes(statusCodes);

        bean.setDefaultErrorView("common/error/systemError");
        bean.setDefaultStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return bean;
    }

    /**
     * Configure messages logging AOP.
     * @param exceptionLogger Bean defined by ApplicationContext#exceptionLogger
     * @see xxxxxx.yyyyyy.zzzzzz.config.app.ApplicationContext#exceptionLogger(ExceptionCodeResolver)
     * @return  Bean of configured {@link HandlerExceptionResolverLoggingInterceptor}
     */
    @Bean("handlerExceptionResolverLoggingInterceptor")
    public HandlerExceptionResolverLoggingInterceptor handlerExceptionResolverLoggingInterceptor(ExceptionLogger exceptionLogger) {
        HandlerExceptionResolverLoggingInterceptor bean = new HandlerExceptionResolverLoggingInterceptor();
        bean.setExceptionLogger(exceptionLogger);
        return bean;
    }

    /**
     * Configure messages logging AOP advisor.
     * @param handlerExceptionResolverLoggingInterceptor Bean defined by #handlerExceptionResolverLoggingInterceptor
     * @see #handlerExceptionResolverLoggingInterceptor(ExceptionLogger)
     * @return Advisor configured for PointCut
     */
    @Bean
    public Advisor handlerExceptionResolverLoggingInterceptorAdvisor
            (HandlerExceptionResolverLoggingInterceptor handlerExceptionResolverLoggingInterceptor) {
       AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
       pointcut.setExpression("execution(* org.springframework.web.servlet.HandlerExceptionResolver.resolveException(..))");
       return new DefaultPointcutAdvisor(pointcut, handlerExceptionResolverLoggingInterceptor);
    }
}