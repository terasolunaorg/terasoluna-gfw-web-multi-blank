/*
 * Copyright(c) 2023 NTT Corporation.
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
package xxxxxx.yyyyyy.zzzzzz.config.web;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.LinkedHashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.access.DelegatingAccessDeniedHandler;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.terasoluna.gfw.security.web.logging.UserIdMDCPutFilter;

/**
 * Bean definition to configure SpringSecurity.
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    /**
     * Configure ignore security pattern.
     * @return Bean of configured {@link WebSecurityCustomizer}
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                new AntPathRequestMatcher("/resources/**"));
    }

    /**
     * Configure {@link SecurityFilterChain} bean.
     * @param http Builder class for setting up authentication and authorization
     * @return Bean of configured {@link SecurityFilterChain}
     * @throws Exception Exception that occurs when setting HttpSecurity
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin(login -> withDefaults());
        http.logout(logout -> withDefaults());
        http.exceptionHandling(ex -> ex.accessDeniedHandler(
                accessDeniedHandler()));
        http.addFilterAfter(userIdMDCPutFilter(),
                AnonymousAuthenticationFilter.class);
        http.sessionManagement(sessionManagement -> withDefaults());
        http.authorizeHttpRequests(authz -> authz.requestMatchers(
                new AntPathRequestMatcher("/**")).permitAll());

        return http.build();
    }

    /**
     * Configure {@link AccessDeniedHandler} bean.
     * @return Bean of configured {@link AccessDeniedHandler}
     */
    @Bean("accessDeniedHandler")
    public AccessDeniedHandler accessDeniedHandler() {
        LinkedHashMap<Class<? extends AccessDeniedException>, AccessDeniedHandler> errorHandlers = new LinkedHashMap<>();

        // Invalid CSRF authenticator error handler
        AccessDeniedHandlerImpl invalidCsrfTokenErrorHandler = new AccessDeniedHandlerImpl();
        invalidCsrfTokenErrorHandler.setErrorPage(
                "/WEB-INF/views/common/error/invalidCsrfTokenError.jsp");
        errorHandlers.put(InvalidCsrfTokenException.class,
                invalidCsrfTokenErrorHandler);

        // Missing CSRF authenticator error handler
        AccessDeniedHandlerImpl missingCsrfTokenErrorHandler = new AccessDeniedHandlerImpl();
        missingCsrfTokenErrorHandler.setErrorPage(
                "/WEB-INF/views/common/error/missingCsrfTokenError.jsp");
        errorHandlers.put(MissingCsrfTokenException.class,
                missingCsrfTokenErrorHandler);

        // Default error handler
        AccessDeniedHandlerImpl defaultErrorHandler = new AccessDeniedHandlerImpl();
        defaultErrorHandler.setErrorPage(
                "/WEB-INF/views/common/error/accessDeniedError.jsp");

        return new DelegatingAccessDeniedHandler(errorHandlers, defaultErrorHandler);
    }

    /**
     * Configure {@link DefaultWebSecurityExpressionHandler} bean.
     * @return Bean of configured {@link DefaultWebSecurityExpressionHandler}
     */
    @Bean("webSecurityExpressionHandler")
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
        return new DefaultWebSecurityExpressionHandler();
    }

    /**
     * Configure {@link UserIdMDCPutFilter} bean.
     * @return Bean of configured {@link UserIdMDCPutFilter}
     */
    @Bean("userIdMDCPutFilter")
    public UserIdMDCPutFilter userIdMDCPutFilter() {
        return new UserIdMDCPutFilter();
    }
}
