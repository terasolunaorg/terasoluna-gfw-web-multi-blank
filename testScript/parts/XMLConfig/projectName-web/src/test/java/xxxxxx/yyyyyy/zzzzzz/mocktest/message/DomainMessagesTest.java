package xxxxxx.yyyyyy.zzzzzz.mocktest.message;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import jakarta.inject.Inject;

@ExtendWith(SpringExtension.class)
@ContextHierarchy({
        @ContextConfiguration({"classpath:META-INF/spring/applicationContext.xml",
                "classpath:META-INF/spring/spring-security.xml"}),
        @ContextConfiguration({"classpath:META-INF/spring/spring-mvc.xml"})})
@WebAppConfiguration
public class DomainMessagesTest {

    @Inject
    private MessageSource messageSource;

    @Test
    public void testDomainMessageIsLoaded() {
        assertEquals("domain test message",
                messageSource.getMessage("test.domain.message", null, Locale.ROOT));
    }
}
