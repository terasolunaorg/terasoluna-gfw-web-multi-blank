package xxxxxx.yyyyyy.zzzzzz.app.common.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/common/error")
public class CommonErrorController {

    @RequestMapping("/accessDeniedError")
    public String accessDeniedError() {
        return "common/error/accessDeniedError";
    }

    @RequestMapping("/businessError")
    public String businessError() {
        return "common/error/businessError";
    }

    @RequestMapping("/dataAccessError")
    public String dataAccessError() {
        return "common/error/dataAccessError";
    }

    @RequestMapping("/invalidCsrfTokenError")
    public String invalidCsrfTokenError() {
        return "common/error/invalidCsrfTokenError";
    }

    @RequestMapping("/missingCsrfTokenError")
    public String missingCsrfTokenError() {
        return "common/error/missingCsrfTokenError";
    }

    @RequestMapping("/resourceNotFoundError")
    public String resourceNotFoundError() {
        return "common/error/resourceNotFoundError";
    }

    @RequestMapping("/systemError")
    public String systemError() {
        return "common/error/systemError";
    }

    @RequestMapping("/transactionTokenError")
    public String transactionTokenError() {
        return "common/error/transactionTokenError";
    }

}
