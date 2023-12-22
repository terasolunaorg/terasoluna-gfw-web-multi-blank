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
