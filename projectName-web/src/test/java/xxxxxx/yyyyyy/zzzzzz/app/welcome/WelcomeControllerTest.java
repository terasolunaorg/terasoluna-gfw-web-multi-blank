package xxxxxx.yyyyyy.zzzzzz.app.welcome;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Locale;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.support.BindingAwareModelMap;

/**
 * Executes the test of handling requests for the application home page.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:META-INF/spring/testContext.xml" })
public class WelcomeControllerTest {

    @Inject
    private WelcomeController welcomeController;

    /**
     * Asserts that the returning view name is "welcome/home".
     */
    @Test
    public void testHome() throws Exception {
        String viewName = welcomeController.home(Locale.getDefault(),
                new BindingAwareModelMap());
        assertThat(viewName, is("welcome/home"));
    }
}
