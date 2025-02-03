package xxxxxx.yyyyyy.zzzzzz.mocktest.error;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import jakarta.inject.Inject;

import xxxxxx.yyyyyy.zzzzzz.config.app.ApplicationContextConfig;
import xxxxxx.yyyyyy.zzzzzz.config.web.SpringMvcConfig;
import xxxxxx.yyyyyy.zzzzzz.config.web.SpringSecurityConfig;

/**
 * Run test for Csrf Token error.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration(
                classes = {ApplicationContextConfig.class, SpringSecurityConfig.class}),
        @ContextConfiguration(classes = {SpringMvcConfig.class})})
@WebAppConfiguration
public class CsrfTokenErrorTest {

    private static final Logger logger = LoggerFactory.getLogger(CsrfTokenErrorTest.class);

    @Inject
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Value("${invalidCsrfTokenError.forwardedUrl}")
    private String invalidCsrfTokenErrorForwardedUrl;

    @Value("${missingCsrfTokenError.forwardedUrl}")
    private String missingCsrfTokenErrorForwardedUrl;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).alwaysDo(log())
                .apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }

    /**
     * Invalid CSRF token error page. Confirm that you are transitioning to the above page.
     * @throws Exception
     */
    @Test
    public void testInvalidCsrfTokenError() throws Exception {

        // Mockmvc test.
        ResultActions results = mockMvc.perform(post("/").with(csrf().useInvalidToken()));

        logger.debug("testInvalidCsrfTokenError#status:"
                + results.andReturn().getResponse().getStatus());
        logger.debug("testInvalidCsrfTokenError#forwardedUrl:"
                + results.andReturn().getResponse().getForwardedUrl());

        results.andExpect(status().is(403))
                .andExpect(forwardedUrl(invalidCsrfTokenErrorForwardedUrl));
    }

    /**
     * CSRF token missing error page. Confirm that you are transitioning to the above page.
     * @throws Exception
     */
    @Test
    public void testMissingCsrfTokenError() throws Exception {

        // Mockmvc test.
        ResultActions results = mockMvc.perform(post("/"));

        logger.debug("testMissingCsrfTokenError#status:"
                + results.andReturn().getResponse().getStatus());
        logger.debug("testMissingCsrfTokenError#forwardedUrl:"
                + results.andReturn().getResponse().getForwardedUrl());

        results.andExpect(status().is(403))
                .andExpect(forwardedUrl(missingCsrfTokenErrorForwardedUrl));
    }

    @After
    public void tearDown() {}
}
