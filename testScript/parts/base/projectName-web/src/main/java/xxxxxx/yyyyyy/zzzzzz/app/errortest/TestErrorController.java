/*
 * Copyright(c) 2023 NTT Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package xxxxxx.yyyyyy.zzzzzz.app.errortest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

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
