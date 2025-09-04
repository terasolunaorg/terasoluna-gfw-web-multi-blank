package xxxxxx.yyyyyy.zzzzzz.mocktest.error;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;
import org.springframework.web.context.WebApplicationContext;
import jakarta.inject.Inject;

import xxxxxx.yyyyyy.zzzzzz.config.app.ApplicationContextConfig;
import xxxxxx.yyyyyy.zzzzzz.config.web.SpringMvcConfig;
import xxxxxx.yyyyyy.zzzzzz.config.web.SpringSecurityConfig;

/**
 * Run test for Csrf Token error.
 */
@ExtendWith(SpringExtension.class)
@ContextHierarchy({
        @ContextConfiguration(
                classes = {ApplicationContextConfig.class, SpringSecurityConfig.class}),
        @ContextConfiguration(classes = {SpringMvcConfig.class})})
@WebAppConfiguration
public class CsrfTokenErrorTest {

    private static final Logger logger = LoggerFactory.getLogger(CsrfTokenErrorTest.class);

    @Inject
    private WebApplicationContext webApplicationContext;

    private MockMvcTester mockMvc;

    @Value("${invalidCsrfTokenError.forwardedUrl}")
    private String invalidCsrfTokenErrorForwardedUrl;

    @Value("${missingCsrfTokenError.forwardedUrl}")
    private String missingCsrfTokenErrorForwardedUrl;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcTester.from(webApplicationContext, builder -> builder.alwaysDo(log())
                .apply(SecurityMockMvcConfigurers.springSecurity()).build());
    }

    /**
     * Invalid CSRF token error page. Confirm that you are transitioning to the above page.
     * @throws Exception
     */
    @Test
    public void testInvalidCsrfTokenError() throws Exception {

        // Mockmvc test.
        MvcTestResult results = mockMvc.post().uri("/").with(csrf().useInvalidToken()).exchange();

        logger.debug("testInvalidCsrfTokenError#status:" + results.getResponse().getStatus());
        logger.debug("testInvalidCsrfTokenError#forwardedUrl:"
                + results.getResponse().getForwardedUrl());

        assertThat(results).hasStatus(403).hasForwardedUrl(invalidCsrfTokenErrorForwardedUrl);
    }

    /**
     * CSRF token missing error page. Confirm that you are transitioning to the above page.
     * @throws Exception
     */
    @Test
    public void testMissingCsrfTokenError() throws Exception {

        // Mockmvc test.
        MvcTestResult results = mockMvc.post().uri("/").exchange();

        logger.debug("testMissingCsrfTokenError#status:" + results.getResponse().getStatus());
        logger.debug("testMissingCsrfTokenError#forwardedUrl:"
                + results.getResponse().getForwardedUrl());

        assertThat(results).hasStatus(403).hasForwardedUrl(missingCsrfTokenErrorForwardedUrl);
    }

    @AfterEach
    public void tearDown() {}
}
