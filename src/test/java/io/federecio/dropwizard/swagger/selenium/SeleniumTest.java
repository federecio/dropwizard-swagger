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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public abstract class SeleniumTest {

    private static final int WAIT_IN_SECONDS = 5;
    private FirefoxDriver driver;

    protected abstract String getSwaggerUrl();

    @Before
    public void setUpTests() {
        driver = new FirefoxDriver();
    }

    @After
    public void terminate() {
        if (driver != null) {
            driver.kill();
        }
    }

    @Test
    public void testResourceIsAccessibleThroughUI() throws Exception {
        driver.get(getSwaggerUrl() + "#!/test/dummyEndpoint_get_0");
        driver.manage().timeouts().implicitlyWait(WAIT_IN_SECONDS, TimeUnit.SECONDS);

        clickOnTryOut();
        assertResponseCodeIs200();
    }

    private void assertResponseCodeIs200() {
        By xpath = By.xpath("//div[@class='block response_code']/pre");
        new WebDriverWait(driver, WAIT_IN_SECONDS).until(ExpectedConditions.textToBePresentInElementLocated(xpath, "200"));
    }

    private void clickOnTryOut() {
        By xpath = By.xpath("//input[@value='Try it out!']");
        new WebDriverWait(driver, WAIT_IN_SECONDS).until(ExpectedConditions.presenceOfElementLocated(xpath));
        driver.findElement(xpath).click();
    }
}
