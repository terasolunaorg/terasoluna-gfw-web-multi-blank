package xxxxxx.yyyyyy.zzzzzz.mocktest.interceptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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

import xxxxxx.yyyyyy.zzzzzz.domain.service.errortest.MockTestService;

/**
 * Run the interceptor test.
 */
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextHierarchy({
        @ContextConfiguration({"classpath:META-INF/spring/applicationContext.xml",
                "classpath:META-INF/spring/spring-security.xml",
                "classpath:META-INF/spring/spring-mvc-mockmvc.xml"}),
        @ContextConfiguration({"classpath:META-INF/spring/spring-mvc.xml"})})
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

    @BeforeEach
    public void setUp() {
        Mockito.reset(mockTestService);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).alwaysDo(log()).build();
    }

    /**
     * Test whether the log is output by TraceLoggingInterceptor.
     * @throws Exception
     */
    @Test
    public void testTraceLoggingInterceptor() throws Exception {

        // Mock the Appender and capture the output contents.
        logger = (Logger) LoggerFactory.getLogger(TraceLoggingInterceptor.class);
        logger.addAppender(mockAppender);

        // Mockmvc test.
        mockMvc.perform(get("/test/mock/success"));

        // Capture logs for 3 runs.
        verify(mockAppender, times(3)).doAppend(captorLoggingEvent.capture());
        List<LoggingEvent> events = captorLoggingEvent.getAllValues();

        assertThat(events.get(0).getLevel()).isEqualTo(Level.TRACE);
        assertThat(events.get(0).getLoggerName())
                .isEqualTo("org.terasoluna.gfw.web.logging.TraceLoggingInterceptor");
        assertThat(events.get(0).getFormattedMessage())
                .isEqualTo("[START CONTROLLER] MockTestController.test(Model)");

        assertThat(events.get(1).getLevel()).isEqualTo(Level.TRACE);
        assertThat(events.get(1).getLoggerName())
                .isEqualTo("org.terasoluna.gfw.web.logging.TraceLoggingInterceptor");
        assertThat(events.get(1).getFormattedMessage()).isEqualTo(
                "[END CONTROLLER  ] MockTestController.test(Model)-> view=welcome/home, model={}");

        assertThat(events.get(2).getLevel()).isEqualTo(Level.TRACE);
        assertThat(events.get(2).getLoggerName())
                .isEqualTo("org.terasoluna.gfw.web.logging.TraceLoggingInterceptor");
        assertThat(events.get(2).getFormattedMessage()
                .matches("^\\[HANDLING TIME   \\] MockTestController\\.test\\(Model\\)-> .+"))
                        .isEqualTo(true);
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

        doThrow(new SystemException("e.xx.fw.9001", "SystemError Test.")).when(mockTestService)
                .testExecute();

        // Mockmvc test.
        mockMvc.perform(get("/test/mock/success"));

        // Capture logs for 1 runs.
        verify(mockAppender, times(1)).doAppend(captorLoggingEvent.capture());
        LoggingEvent event = captorLoggingEvent.getValue();

        assertThat(event.getLevel()).isEqualTo(Level.ERROR);
        assertThat(event.getLoggerName())
                .isEqualTo("org.terasoluna.gfw.common.exception.ExceptionLogger");
        assertThat(event.getFormattedMessage()).isEqualTo("[e.xx.fw.9001] SystemError Test.");
        assertThat(event.getThrowableProxy().getClassName())
                .isEqualTo("org.terasoluna.gfw.common.exception.SystemException");
    }

    @AfterEach
    public void tearDown() {}
}
