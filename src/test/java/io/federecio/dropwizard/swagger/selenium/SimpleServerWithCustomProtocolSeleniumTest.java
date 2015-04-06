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

import static org.junit.Assert.assertEquals;
import io.federecio.dropwizard.junitrunner.DropwizardJunitRunner;
import io.federecio.dropwizard.junitrunner.DropwizardTestConfig;
import io.federecio.dropwizard.swagger.TestApplicationWithCustomProtocol;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@DropwizardTestConfig(applicationClass = TestApplicationWithCustomProtocol.class, yamlFile = "/test-simple-root-path.yaml")
@RunWith(DropwizardJunitRunner.class)
public class SimpleServerWithCustomProtocolSeleniumTest extends SeleniumTest {

    @Override
    protected String getSwaggerUrl() {
        return "http://localhost:55668/swagger";
    }

    @Test
    public void testResourceIsAccessibleThroughUI() throws Exception {
        driver.get(getSwaggerUrl() + "#!/test/dummyEndpoint");
        driver.manage().timeouts().implicitlyWait(WAIT_IN_SECONDS, TimeUnit.SECONDS);

        clickOnTryOut();
        assertResponseCodeIs0();

        By xpath = By.xpath("//div[@class='block request_url']/pre");
        assertEquals("https://localhost:55668/test.json", driver.findElement(xpath).getText());
    }

    private void assertResponseCodeIs0() {
        By xpath = By.xpath("//div[@class='block response_code']/pre");
        new WebDriverWait(driver, WAIT_IN_SECONDS).until(ExpectedConditions.textToBePresentInElementLocated(xpath, "0"));
    }
}
