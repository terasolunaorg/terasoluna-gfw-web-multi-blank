package xxxxxx.yyyyyy.zzzzzz.mocktest.error;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.inject.Inject;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Run the access denied error test.
 */
@ExtendWith(SpringExtension.class)
@ContextHierarchy({
        @ContextConfiguration({"classpath:META-INF/spring/applicationContext.xml",
                "classpath:META-INF/spring/spring-security.xml"}),
        @ContextConfiguration("classpath:META-INF/spring/spring-mvc.xml")})
@WebAppConfiguration
public class AccessDeniedErrorTest {

    private static final Logger logger = LoggerFactory.getLogger(AccessDeniedErrorTest.class);

    @Inject
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Inject
    private AccessDeniedHandler accessDeniedHandler;

    @Value("${accessDeniedError.forwardedUrl}")
    private String accessDeniedErrorForwardedUrl;

    @BeforeEach
    public void setUp() {
        // Set Filter for Test.
        DefaultSecurityFilterChain chain =
                new DefaultSecurityFilterChain(AnyRequestMatcher.INSTANCE, new TestFilter());
        TestFilterChainProxy chainProxy = new TestFilterChainProxy(chain);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).alwaysDo(log())
                .apply(SecurityMockMvcConfigurers.springSecurity(chainProxy)).build();
    }

    /**
     * access denied error page. Confirm that you are transitioning to the above page.
     * @throws Exception
     */
    @Test
    public void testAccessDeniedErrorMockMvc() throws Exception {

        // Mockmvc test.
        ResultActions results = mockMvc.perform(get("/"));

        logger.debug(
                "testAccessDeniedError#status:" + results.andReturn().getResponse().getStatus());
        logger.debug("testAccessDeniedError#forwardedUrl:"
                + results.andReturn().getResponse().getForwardedUrl());

        results.andExpect(status().is(403)).andExpect(forwardedUrl(accessDeniedErrorForwardedUrl));
    }

    /** Test FilterChainProxy */
    public class TestFilterChainProxy extends FilterChainProxy {

        public TestFilterChainProxy(SecurityFilterChain chain) {
            super(chain);
        }
    }

    /** Test filter */
    public class TestFilter extends OncePerRequestFilter {

        public TestFilter() {}

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                FilterChain filterChain) throws ServletException, IOException {
            // Since I can't reproduce the error, I run the handler directly.
            accessDeniedHandler.handle(request, response,
                    new AccessDeniedException("Access Denied Error Test."));
        }
    }

    @AfterEach
    public void tearDown() {}

}
