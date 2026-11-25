package xxxxxx.yyyyyy.zzzzzz.mocktest.message;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Locale;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import jakarta.inject.Inject;

import xxxxxx.yyyyyy.zzzzzz.config.app.ApplicationContextConfig;
import xxxxxx.yyyyyy.zzzzzz.config.web.SpringMvcConfig;
import xxxxxx.yyyyyy.zzzzzz.config.web.SpringSecurityConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration(classes = {ApplicationContextConfig.class, SpringSecurityConfig.class}),
        @ContextConfiguration(classes = {SpringMvcConfig.class})})
@WebAppConfiguration
public class DomainMessagesTest {

    @Inject
    private MessageSource messageSource;

    @Test
    public void testDomainMessageIsLoaded() {
        assertThat(messageSource.getMessage("test.domain.message", null, Locale.ROOT),
                is("domain test message"));
    }
}
