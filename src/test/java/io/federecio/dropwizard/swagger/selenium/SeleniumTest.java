// Copyright (C) 2014 Federico Recio
/**
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

import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class SeleniumTest {

    private static ChromeDriverService service;
    protected static final int WAIT_IN_SECONDS = 1000;
    protected WebDriver driver;

    protected String getSwaggerUrl(int port, String path) {
        return String.format("http://127.0.0.1:%d%s", port, path);
    }

    protected abstract String getSwaggerUrl();

    @BeforeClass
    public static void createAndStartService() throws Exception {
        service = new ChromeDriverService.Builder().withSilent(true)
                .usingAnyFreePort().build();
        service.start();
    }

    @AfterClass
    public static void stopService() {
        service.stop();
    }

    @Before
    public void setUpTests() {
        driver = new ChromeDriver();
    }

    @After
    public void terminate() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testResourceIsAccessibleThroughUI() throws Exception {
        driver.get(getSwaggerUrl() + "#!/test/dummyEndpoint");
        driver.manage().timeouts().implicitlyWait(WAIT_IN_SECONDS,
                TimeUnit.SECONDS);

        clickOnTryOut("test_dummyEndpoint_content");
        assertResponseCodeIs("test_dummyEndpoint_content", 200);
    }

    protected void assertResponseCodeIs(String contentId, int code) {
        By xpath = By.xpath(String.format(
                "//div[@id='%s']/div[@class='response']/div[@class='block response_code']/pre",
                contentId));
        new WebDriverWait(driver, WAIT_IN_SECONDS).until(ExpectedConditions
                .textToBePresentInElementLocated(xpath, String.valueOf(code)));
    }

    protected void assertResponseBodyIs(String contentId, String body) {
        By xpath = By.xpath(String.format(
                "//div[@id='%s']/div[@class='response']/div[@class='block response_body hljs']/pre/code",
                contentId));
        new WebDriverWait(driver, WAIT_IN_SECONDS).until(ExpectedConditions
                .textToBePresentInElementLocated(xpath, body));
    }

    protected void clickOnTryOut(String contentId) {
        By xpath = By.xpath(String.format(
                "//div[@id='%s']/form/div[@class='sandbox_header']/input[@value='Try it out!']",
                contentId));
        new WebDriverWait(driver, WAIT_IN_SECONDS)
                .until(ExpectedConditions.visibilityOfElementLocated(xpath));
        driver.findElement(xpath).click();
    }
}
