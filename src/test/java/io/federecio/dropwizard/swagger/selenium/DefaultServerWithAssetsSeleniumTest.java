/**
 * Copyright (C) 2014 Federico Recio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.federecio.dropwizard.swagger.selenium;

import io.federecio.dropwizard.junitrunner.DropwizardJunitRunner;
import io.federecio.dropwizard.junitrunner.DropwizardTestConfig;
import io.federecio.dropwizard.swagger.Constants;
import io.federecio.dropwizard.swagger.TestApplicationWithAssetsAndPathSetProgramatically;
import io.federecio.dropwizard.swagger.TestApplicationWithPathSetProgramatically;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * @author Federico Recio
 */
@RunWith(DropwizardJunitRunner.class)
@DropwizardTestConfig(applicationClass = TestApplicationWithAssetsAndPathSetProgramatically.class, yamlFile = "/test-default-assets.yaml")
public class DefaultServerWithAssetsSeleniumTest extends SeleniumTest {

    public static final String BASE_URL = "http://localhost:33355";
    private static final String BASE_URL_WITH_BASE_PATH = BASE_URL + TestApplicationWithPathSetProgramatically.BASE_PATH;

    @Override
    protected String getSwaggerUrl() {
        return BASE_URL_WITH_BASE_PATH + Constants.SWAGGER_PATH;
    }

    @Test
    public void testApplicationAssetsAreAccessible() throws Exception {
        driver.get(BASE_URL + "/test.html");
        driver.manage().timeouts().implicitlyWait(WAIT_IN_SECONDS, TimeUnit.SECONDS);

        By xpath = By.xpath("//h1");
        new WebDriverWait(driver, WAIT_IN_SECONDS).until(ExpectedConditions.textToBePresentInElementLocated(xpath, "test asset"));
    }
}
