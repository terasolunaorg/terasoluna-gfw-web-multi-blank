/*@
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

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.terasoluna.gfw.web.logging.HttpSessionEventLoggingListener;
import org.terasoluna.gfw.web.logging.mdc.MDCClearFilter;
import org.terasoluna.gfw.web.logging.mdc.XTrackMDCPutFilter;

import xxxxxx.yyyyyy.zzzzzz.config.app.ApplicationContextConfig;
import xxxxxx.yyyyyy.zzzzzz.config.web.SpringMvcConfig;
import xxxxxx.yyyyyy.zzzzzz.config.web.SpringSecurityConfig;

import ch.qos.logback.classic.servlet.LogbackServletContextListener;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterRegistration;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;

/**
 * Booting Spring web applications. Alternative class for web.xml.
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * The Application ServletName.
     */
    private static final String APP_SERVLET_NAME = "appServlet";

    /**
     * {@inheritDoc}
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.setInitParameter("logbackDisableServletContainerInitializer", "true");
        super.onStartup(servletContext);
        addListeners(servletContext);
        addFilters(servletContext);
    }

    /**
     * Add listeners to ServletContext.
     * @param servletContext Accepted at onStartup<br>
     *                       Add information about {@link EventListener}
     */
    private void addListeners(ServletContext servletContext) {
        servletContext.addListener(LogbackServletContextListener.class);
        servletContext.addListener(HttpSessionEventLoggingListener.class);
    }

    /**
     * Add Filters to ServletContext.
     * @param servletContext Accepted at onStartup<br>
     *                       Add information about {@link Filter}
     */
    private void addFilters(ServletContext servletContext) {
        addFilter(servletContext, "MDCClearFilter", new MDCClearFilter());
        addFilter(servletContext, "exceptionLoggingFilter", new DelegatingFilterProxy("exceptionLoggingFilter"));
        addFilter(servletContext, "XTrackMDCPutFilter", new XTrackMDCPutFilter());
        addFilter(servletContext, "CharacterEncodingFilter", characterEncodingFilter());
        addFilter(servletContext, "springSecurityFilterChain", new DelegatingFilterProxy("springSecurityFilterChain"));
    }

    /**
     * Common processes used in #addFilters.
     * @param servletContext Accepted at onStartup
     * @param filterName Name of the filter defined in #addFilters
     * @param filter Filters to be configured
     */
    private void addFilter(ServletContext servletContext, String filterName, Filter filter) {
        FilterRegistration.Dynamic filterRegistration = servletContext.addFilter(filterName, filter);
        filterRegistration.addMappingForUrlPatterns(null, false, "/*");
    }

    /**
     * Configure {@link CharacterEncodingFilter}.
     * @return Bean of configured {@link CharacterEncodingFilter}
     */
    private CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {
            ApplicationContextConfig.class,
            SpringSecurityConfig.class
        };
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { SpringMvcConfig.class };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getServletName() {
        return APP_SERVLET_NAME;
    }
}