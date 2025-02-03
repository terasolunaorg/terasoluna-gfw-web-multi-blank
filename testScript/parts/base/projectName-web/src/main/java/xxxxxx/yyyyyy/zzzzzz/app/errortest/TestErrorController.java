package xxxxxx.yyyyyy.zzzzzz.app.errortest;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Error page test controller
 */
@Controller
@RequestMapping("/test/error")
public class TestErrorController {

    @GetMapping("/accessDeniedError")
    public String accessDeniedError(HttpServletRequest request, Model model) {
        model.addAttribute("exceptionCode", "e.xx.fw.7003");
        return "common/error/accessDeniedError";
    }

    @GetMapping("/businessError")
    public String businessError(HttpServletRequest request, Model model) {
        model.addAttribute("exceptionCode", "e.xx.fw.8001");
        return "common/error/businessError";
    }

    @GetMapping("/dataAccessError")
    public String dataAccessError(HttpServletRequest request, Model model) {
        model.addAttribute("exceptionCode", "e.xx.fw.9002");
        return "common/error/dataAccessError";
    }

    @GetMapping("/invalidCsrfTokenError")
    public String invalidCsrfTokenError(HttpServletRequest request,
            Model model) {
        model.addAttribute("exceptionCode", "e.xx.fw.7002");
        return "common/error/invalidCsrfTokenError";
    }

    @GetMapping("/missingCsrfTokenError")
    public String missingCsrfTokenError(HttpServletRequest request,
            Model model) {
        model.addAttribute("exceptionCode", "e.xx.fw.7004");
        return "common/error/missingCsrfTokenError";
    }

    @GetMapping("/resourceNotFoundError")
    public String resourceNotFoundError(HttpServletRequest request,
            Model model) {
        model.addAttribute("exceptionCode", "e.xx.fw.5001");
        return "common/error/resourceNotFoundError";
    }

    @GetMapping("/systemError")
    public String systemError(HttpServletRequest request, Model model) {
        model.addAttribute("exceptionCode", "e.xx.fw.9001");
        return "common/error/systemError";
    }

    @GetMapping("/transactionTokenError")
    public String transactionTokenError(HttpServletRequest request,
            Model model) {
        model.addAttribute("exceptionCode", "e.xx.fw.7001");
        return "common/error/transactionTokenError";
    }

    @GetMapping("/other")
    public String unhandledSystemError(HttpServletRequest request) {
        return "errortest/otherError";
    }
}
