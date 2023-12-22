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
package xxxxxx.yyyyyy.zzzzzz.selenium.welcome;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import xxxxxx.yyyyyy.zzzzzz.selenium.FunctionTestSupport;

/**
 * Executes the test for the application home page.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:META-INF/spring/seleniumContext.xml" })
public class HelloIT extends FunctionTestSupport{

    /**
     * Asserts that the content of the application home page is "Hello world!".
     */
    @Test
    public void testHelloWorld() throws IOException {

        assertThat(webDriver.findElement(By.id("title")).getText(),
                is("Hello world!"));
    }
}
