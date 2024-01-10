/*
 * Copyright(c) 2023 NTT DATA Corporation.
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
package xxxxxx.yyyyyy.zzzzzz.selenium.webdrivers;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ChromeDriverFactoryBean extends
                                     WebDriverManagerFactoryBean<ChromeDriver> {

    @Override
    public ChromeDriver getObject() {
        if (System.getenv("webdriver.chrome.driver") == null) {
            WebDriverManager.chromedriver().setup();
        }
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        return new ChromeDriver(options);
    }

    @Override
    public Class<?> getObjectType() {
        return ChromeDriver.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

}
