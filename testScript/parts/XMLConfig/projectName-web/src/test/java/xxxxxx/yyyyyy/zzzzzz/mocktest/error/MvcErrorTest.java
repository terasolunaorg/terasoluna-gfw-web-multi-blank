package xxxxxx.yyyyyy.zzzzzz.mocktest.error;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;
import org.terasoluna.gfw.common.exception.SystemException;
import org.terasoluna.gfw.web.token.transaction.InvalidTransactionTokenException;

import jakarta.inject.Inject;
import xxxxxx.yyyyyy.zzzzzz.domain.service.errortest.MockTestService;

/**
 * Run Spring Mvc error test.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({ @ContextConfiguration({
        "classpath:META-INF/spring/applicationContext.xml",
        "classpath:META-INF/spring/spring-security.xml" }),
        @ContextConfiguration({ "classpath:META-INF/spring/spring-mvc.xml",
                "classpath:META-INF/spring/spring-mvc-mockmvc.xml" }) })
@WebAppConfiguration
public class MvcErrorTest {

    private static final Logger logger = LoggerFactory.getLogger(
            MvcErrorTest.class);

    @Inject
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Inject
    MockTestService mockTestService;

    @Before
    public void setUp() {
        Mockito.reset(mockTestService);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .alwaysDo(log()).build();
    }

    /**
     * Business error page. Confirm that you are transitioning to the above page.
     * @throws Exception
     */
    @Test
    public void testBusinessError() throws Exception {

        doThrow(new BusinessException("e.xx.fw.8001")).when(mockTestService)
                .testExecute();

        // Mockmvc test.
        ResultActions results = mockMvc.perform(get("/test/mock/success"));

        logger.debug("testBusinessError#status:" + results.andReturn()
                .getResponse().getStatus());
        logger.debug("testBusinessError#view:" + results.andReturn()
                .getModelAndView().getViewName());

        results.andExpect(status().is(409)).andExpect(view().name(
                "common/error/businessError"));
    }

    /**
     * Data access error page. Confirm that you are transitioning to the above page.
     * @throws Exception
     */
    @Test
    public void testDataAccessError() throws Exception {

        doThrow(new QueryTimeoutException("DataAccessError Test.")).when(
                mockTestService).testExecute();

        // Mockmvc test.
        ResultActions results = mockMvc.perform(get("/test/mock/success"));

        logger.debug("testDataAccessError#status:" + results.andReturn()
                .getResponse().getStatus());
        logger.debug("testDataAccessError#view:" + results.andReturn()
                .getModelAndView().getViewName());

        results.andExpect(status().is(500)).andExpect(view().name(
                "common/error/dataAccessError"));
    }

    /**
     * Error page where the resource does not exist. Confirm that you are transitioning to the above page.
     * @throws Exception
     */
    @Test
    public void testResourceNotFoundError() throws Exception {

        doThrow(new ResourceNotFoundException("ResourceNotFoundError Test."))
                .when(mockTestService).testExecute();

        // Mockmvc test.
        ResultActions results = mockMvc.perform(get("/test/mock/success"));

        logger.debug("testResourceNotFoundError#status:" + results.andReturn()
                .getResponse().getStatus());
        logger.debug("testResourceNotFoundError#view:" + results.andReturn()
                .getModelAndView().getViewName());

        results.andExpect(status().is(404)).andExpect(view().name(
                "common/error/resourceNotFoundError"));
    }

    /**
     * System error page. Confirm that you are transitioning to the above page.
     * @throws Exception
     */
    @Test
    public void testSystemError() throws Exception {

        doThrow(new SystemException("e.xx.fw.9001", "SystemError Test.")).when(
                mockTestService).testExecute();

        // Mockmvc test.
        ResultActions results = mockMvc.perform(get("/test/mock/success"));

        logger.debug("testSystemError#status:" + results.andReturn()
                .getResponse().getStatus());
        logger.debug("testSystemError#view:" + results.andReturn()
                .getModelAndView().getViewName());

        results.andExpect(status().is(500)).andExpect(view().name(
                "common/error/systemError"));
    }

    /**
     * Double submission error page. Confirm that you are transitioning to the above page.
     * @throws Exception
     */
    @Test
    public void testTransactionTokenError() throws Exception {

        doThrow(new InvalidTransactionTokenException()).when(mockTestService)
                .testExecute();

        // Mockmvc test.
        ResultActions results = mockMvc.perform(get("/test/mock/success"));

        logger.debug("testTransactionTokenError#status:" + results.andReturn()
                .getResponse().getStatus());
        logger.debug("testTransactionTokenError#view:" + results.andReturn()
                .getModelAndView().getViewName());

        results.andExpect(status().is(409)).andExpect(view().name(
                "common/error/transactionTokenError"));
    }

    @After
    public void tearDown() {
    }
}
