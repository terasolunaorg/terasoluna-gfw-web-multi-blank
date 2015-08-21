package xxxxxx.yyyyyy.zzzzzz.app.welcome;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

/**
 * Executes the test of handling requests for the application home page.
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:META-INF/spring/testContext.xml" })
public class HelloControllerTest {

    @Inject
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(wac).build();
    }

    /**
     * Asserts that the returning view name is "welcome/home", and that the HTTP
     * Status Code is OK.
     */
    @Test
    public void testHome() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk())
                .andExpect(view().name("welcome/home"));
    }
}
