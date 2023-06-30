package xxxxxx.yyyyyy.zzzzzz.mocktest.interceptor;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.terasoluna.gfw.common.exception.ExceptionLogger;
import org.terasoluna.gfw.common.exception.SystemException;
import org.terasoluna.gfw.web.logging.TraceLoggingInterceptor;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import jakarta.inject.Inject;
import xxxxxx.yyyyyy.zzzzzz.config.SpringMvcMockMvcConfig;
import xxxxxx.yyyyyy.zzzzzz.config.app.ApplicationContextConfig;
import xxxxxx.yyyyyy.zzzzzz.config.web.SpringMvcConfig;
import xxxxxx.yyyyyy.zzzzzz.config.web.SpringSecurityConfig;
import xxxxxx.yyyyyy.zzzzzz.domain.service.errortest.MockTestService;

/**
 * Run the interceptor test.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({ @ContextConfiguration(classes = {
        ApplicationContextConfig.class, SpringSecurityConfig.class }),
        @ContextConfiguration(classes = { SpringMvcConfig.class,
                SpringMvcMockMvcConfig.class }) })
@WebAppConfiguration
public class InterceptorTest {

    @Inject
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private Logger logger;

    @Inject
    MockTestService mockTestService;

    @Mock
    private Appender<ILoggingEvent> mockAppender;

    @Captor
    private ArgumentCaptor<LoggingEvent> captorLoggingEvent;

    @Rule
    public MockitoRule mockito = MockitoJUnit.rule();

    @Before
    public void setUp() {
        Mockito.reset(mockTestService);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .alwaysDo(log()).build();
    }

    /**
     * Test whether the log is output by TraceLoggingInterceptor.
     * @throws Exception
     */
    @Test
    public void testTraceLoggingInterceptor() throws Exception {

        // Mock the Appender and capture the output contents.
        logger = (Logger) LoggerFactory.getLogger(
                TraceLoggingInterceptor.class);
        logger.addAppender(mockAppender);

        // Mockmvc test.
        mockMvc.perform(get("/test/mock/success"));

        // Capture logs for 3 runs.
        verify(mockAppender, times(3)).doAppend(captorLoggingEvent.capture());
        List<LoggingEvent> events = captorLoggingEvent.getAllValues();

        assertThat(events.get(0).getLevel(), is(Level.TRACE));
        assertThat(events.get(0).getLoggerName(), is(
                "org.terasoluna.gfw.web.logging.TraceLoggingInterceptor"));
        assertThat(events.get(0).getFormattedMessage(), is(
                "[START CONTROLLER] MockTestController.test(Model)"));

        assertThat(events.get(1).getLevel(), is(Level.TRACE));
        assertThat(events.get(1).getLoggerName(), is(
                "org.terasoluna.gfw.web.logging.TraceLoggingInterceptor"));
        assertThat(events.get(1).getFormattedMessage(), is(
                "[END CONTROLLER  ] MockTestController.test(Model)-> view=welcome/home, model={}"));

        assertThat(events.get(2).getLevel(), is(Level.TRACE));
        assertThat(events.get(2).getLoggerName(), is(
                "org.terasoluna.gfw.web.logging.TraceLoggingInterceptor"));
        assertThat(events.get(2).getFormattedMessage().matches(
                "^\\[HANDLING TIME   \\] MockTestController\\.test\\(Model\\)-> .+"),
                is(true));
    }

    /**
     * Test whether the log is output by ExceptionResolverLoggingInterceptor.
     * @throws Exception
     */
    @Test
    public void testExceptionResolverLoggingInterceptor() throws Exception {

        // Mock the Appender and capture the output contents.
        logger = (Logger) LoggerFactory.getLogger(ExceptionLogger.class);
        logger.addAppender(mockAppender);

        doThrow(new SystemException("e.xx.fw.9001", "SystemError Test.")).when(
                mockTestService).testExecute();

        // Mockmvc test.
        mockMvc.perform(get("/test/mock/success"));

        // Capture logs for 1 runs.
        verify(mockAppender, times(1)).doAppend(captorLoggingEvent.capture());
        LoggingEvent event = captorLoggingEvent.getValue();

        assertThat(event.getLevel(), is(Level.ERROR));
        assertThat(event.getLoggerName(), is(
                "org.terasoluna.gfw.common.exception.ExceptionLogger"));
        assertThat(event.getFormattedMessage(), is(
                "[e.xx.fw.9001] SystemError Test."));
        assertThat(event.getThrowableProxy().getClassName(), is(
                "org.terasoluna.gfw.common.exception.SystemException"));
    }

    @After
    public void tearDown() {
    }
}
