package xxxxxx.yyyyyy.zzzzzz.app.errortest;

import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import xxxxxx.yyyyyy.zzzzzz.domain.service.errortest.MockTestService;

/**
 * Controller for Mock test
 */
@Controller
@RequestMapping("/test/mock")
public class MockTestController {

    @Inject
    MockTestService mockTestService;

    @GetMapping(value = "/success")
    public String test(Model model) {
        mockTestService.testExecute();
        return "welcome/home";
    }
}
