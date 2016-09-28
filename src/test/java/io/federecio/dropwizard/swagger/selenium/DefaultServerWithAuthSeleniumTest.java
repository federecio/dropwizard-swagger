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

import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import io.federecio.dropwizard.swagger.Constants;
import io.federecio.dropwizard.swagger.TestApplicationWithAuth;
import io.federecio.dropwizard.swagger.TestConfiguration;
import org.junit.ClassRule;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class DefaultServerWithAuthSeleniumTest extends SeleniumTest {

    @ClassRule
    public static final DropwizardAppRule<TestConfiguration> RULE =
            new DropwizardAppRule<>(TestApplicationWithAuth.class, ResourceHelpers.resourceFilePath("test-default.yaml"));

    @Override
    protected String getSwaggerUrl() {
        return getSwaggerUrl(44444, Constants.SWAGGER_PATH);
    }

    @Override
    public void testResourceIsAccessibleThroughUI() throws Exception {
        driver.get(getSwaggerUrl() + "#!/test/dummyEndpoint");
        driver.manage().timeouts().implicitlyWait(WAIT_IN_SECONDS, TimeUnit.SECONDS);

        clickOnTryOut();
        authenticate("test");
        assertResponseCodeIs200();
    }

    // FIXME: authenticateUsing has not been implemented yet and TAB key could not be sent in alert box to type password.
    private void authenticate(String username) {
        WebDriverWait wait = new WebDriverWait(driver, WAIT_IN_SECONDS);
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.sendKeys(username);
        alert.accept();
        driver.switchTo().defaultContent();
    }
}
