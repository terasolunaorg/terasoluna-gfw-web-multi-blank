package xxxxxx.yyyyyy.zzzzzz.mocktest.welcome;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import java.util.List;
import java.util.Locale;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import jakarta.inject.Inject;

import xxxxxx.yyyyyy.zzzzzz.app.welcome.HelloController;

/**
 * Run a unit test on HelloController.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration({"classpath:META-INF/spring/applicationContext.xml",
                "classpath:META-INF/spring/spring-security.xml"}),
        @ContextConfiguration({"classpath:META-INF/spring/spring-mvc.xml"})})
@WebAppConfiguration
public class HelloTest {

    @Inject
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private Logger logger;

    @Mock
    private Appender<ILoggingEvent> mockAppender;

    @Captor
    private ArgumentCaptor<LoggingEvent> captorLoggingEvent;

    @Rule
    public MockitoRule mockito = MockitoJUnit.rule();

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).alwaysDo(log()).build();
    }

    /**
     * Run the HelloController's internal tests.
     * @throws Exception
     */
    @Test
    public void testTraceLoggingInterceptor() throws Exception {

        // Mock the Appender and capture the output contents.
        logger = (Logger) LoggerFactory.getLogger(HelloController.class);
        logger.addAppender(mockAppender);

        // Mockmvc test.
        ResultActions result = mockMvc.perform(get("/").header("Accept-Language", "en"));

        verify(mockAppender, times(1)).doAppend(captorLoggingEvent.capture());
        List<LoggingEvent> events = captorLoggingEvent.getAllValues();

        // Confirmation of log output contents
        assertThat(events.get(0).getLevel(), is(Level.INFO));
        assertThat(events.get(0).getFormattedMessage(),
                is("Welcome home! The client locale is " + Locale.ENGLISH + "."));
        // Confirmation of model settings
        ModelAndView mv = result.andReturn().getModelAndView();
        String serverTime = (String) mv.getModel().get("serverTime");
        assertThat(serverTime.matches(
                "^[a-zA-Z]{3,9}.\\d{1,2},.\\d{4},.\\d{1,2}:\\d{1,2}:\\d{1,2}.(AM|PM)$"), is(true));
    }

    @After
    public void tearDown() {}
}
