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

import jakarta.inject.Inject;
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
