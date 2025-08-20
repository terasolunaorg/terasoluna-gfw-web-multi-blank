package xxxxxx.yyyyyy.zzzzzz.mocktest.error;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;
import org.springframework.web.context.WebApplicationContext;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;
import org.terasoluna.gfw.common.exception.SystemException;
import org.terasoluna.gfw.web.token.transaction.InvalidTransactionTokenException;
import jakarta.inject.Inject;

import xxxxxx.yyyyyy.zzzzzz.config.SpringMvcMockMvcConfig;
import xxxxxx.yyyyyy.zzzzzz.config.app.ApplicationContextConfig;
import xxxxxx.yyyyyy.zzzzzz.config.web.SpringMvcConfig;
import xxxxxx.yyyyyy.zzzzzz.config.web.SpringSecurityConfig;
import xxxxxx.yyyyyy.zzzzzz.domain.service.errortest.MockTestService;

/**
 * Run Spring Mvc error test.
 */
@ExtendWith(SpringExtension.class)
@ContextHierarchy({
        @ContextConfiguration(classes = {ApplicationContextConfig.class, SpringSecurityConfig.class,
                SpringMvcMockMvcConfig.class}),
        @ContextConfiguration(classes = {SpringMvcConfig.class})})
@WebAppConfiguration
public class MvcErrorTest {

    private static final Logger logger = LoggerFactory.getLogger(MvcErrorTest.class);

    @Inject
    private WebApplicationContext webApplicationContext;

    private MockMvcTester mockMvc;

    @Inject
    MockTestService mockTestService;

    @BeforeEach
    public void setUp() {
        Mockito.reset(mockTestService);
        mockMvc = MockMvcTester.from(webApplicationContext,
                builder -> builder.alwaysDo(log()).build());
    }

    /**
     * Business error page. Confirm that you are transitioning to the above page.
     * @throws Exception
     */
    @Test
    public void testBusinessError() throws Exception {

        doThrow(new BusinessException("e.xx.fw.8001")).when(mockTestService).testExecute();

        // Mockmvc test.
        MvcTestResult results = mockMvc.get().uri("/test/mock/success").exchange();

        logger.debug("testBusinessError#status:" + results.getResponse().getStatus());
        logger.debug(
                "testBusinessError#view:" + results.getMvcResult().getModelAndView().getViewName());

        // results.andExpect(status().is(409)).andExpect(view().name("common/error/businessError"));
        assertThat(results).hasStatus(409).hasViewName("common/error/businessError");
    }

    /**
     * Data access error page. Confirm that you are transitioning to the above page.
     * @throws Exception
     */
    @Test
    public void testDataAccessError() throws Exception {

        doThrow(new QueryTimeoutException("DataAccessError Test.")).when(mockTestService)
                .testExecute();

        // Mockmvc test.
        MvcTestResult results = mockMvc.get().uri("/test/mock/success").exchange();

        logger.debug("testDataAccessError#view:"
                + results.getMvcResult().getModelAndView().getViewName());

        assertThat(results).hasStatus(500).hasViewName("common/error/dataAccessError");
    }

    /**
     * Error page where the resource does not exist. Confirm that you are transitioning to the above
     * page.
     * @throws Exception
     */
    @Test
    public void testResourceNotFoundError() throws Exception {

        doThrow(new ResourceNotFoundException("ResourceNotFoundError Test.")).when(mockTestService)
                .testExecute();

        // Mockmvc test.
        MvcTestResult results = mockMvc.get().uri("/test/mock/success").exchange();

        logger.debug("testResourceNotFoundError#status:" + results.getResponse().getStatus());
        logger.debug("testResourceNotFoundError#view:"
                + results.getMvcResult().getModelAndView().getViewName());

        assertThat(results).hasStatus(404).hasViewName("common/error/resourceNotFoundError");
    }

    /**
     * System error page. Confirm that you are transitioning to the above page.
     * @throws Exception
     */
    @Test
    public void testSystemError() throws Exception {

        doThrow(new SystemException("e.xx.fw.9001", "SystemError Test.")).when(mockTestService)
                .testExecute();

        // Mockmvc test.
        MvcTestResult results = mockMvc.get().uri("/test/mock/success").exchange();

        logger.debug("testSystemError#status:" + results.getResponse().getStatus());
        logger.debug("testSystemError#view:" + results.getMvcResult().getModelAndView().getViewName());

        assertThat(results).hasStatus(500).hasViewName("common/error/systemError");
    }

    /**
     * Double submission error page. Confirm that you are transitioning to the above page.
     * @throws Exception
     */
    @Test
    public void testTransactionTokenError() throws Exception {

        doThrow(new InvalidTransactionTokenException()).when(mockTestService).testExecute();

        // Mockmvc test.
        MvcTestResult results = mockMvc.get().uri("/test/mock/success").exchange();

        logger.debug("testTransactionTokenError#status:" + results.getResponse().getStatus());
        logger.debug("testTransactionTokenError#view:" + results.getMvcResult().getModelAndView().getViewName());

        assertThat(results).hasStatus(409).hasViewName("common/error/transactionTokenError");
    }

    @AfterEach
    public void tearDown() {}
}
