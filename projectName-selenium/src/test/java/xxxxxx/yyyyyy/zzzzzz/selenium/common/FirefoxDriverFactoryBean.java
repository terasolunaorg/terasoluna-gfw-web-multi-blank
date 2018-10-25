/*
 * Copyright (C) 2013-2018 NTT DATA Corporation
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
package xxxxxx.yyyyyy.zzzzzz.selenium.common;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;

import io.github.bonigarcia.wdm.FirefoxDriverManager;

/**
 * Create the bean of FirefoxDriver.
 * Use WebDriverManager to create WebDriver for operating the browser.
 */
public class FirefoxDriverFactoryBean implements FactoryBean<FirefoxDriver> {

    @Value("${selenium.geckodriverVersion}")
    protected String geckodriverVersion;

    @Value("${selenium.proxyHttpServer}")
    protected String proxyHttpServer;

    @Value("${selenium.proxyUserName}")
    protected String userName;

    @Value("${selenium.proxyUserPassword}")
    protected String userPassword;

    /**
     * Setup the geckodriver to use Firefox and return the FirefoxDriver.
     */
    @Override
    public FirefoxDriver getObject() {
        if (System.getProperty("webdriver.gecko.driver") == null) {
            FirefoxDriverManager.getInstance().version(geckodriverVersion)
                    .forceCache().proxy(proxyHttpServer).proxyUser(userName)
                    .proxyPass(userPassword).setup();
        }
        FirefoxProfile profile = new FirefoxProfile();
        return new FirefoxDriver(profile);
    }

    @Override
    public Class<?> getObjectType() {
        return FirefoxDriver.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

}
