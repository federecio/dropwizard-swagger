/**
 * Copyright (C) 2014 Federico Recio
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.federecio.dropwizard.swagger.selenium.auth;

import io.federecio.dropwizard.junitrunner.DropwizardJunitRunner;
import io.federecio.dropwizard.junitrunner.DropwizardTestConfig;
import io.federecio.dropwizard.swagger.Constants;
import io.federecio.dropwizard.swagger.selenium.SeleniumTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;

/**
 * @author Maximilien Marie
 */
@RunWith(DropwizardJunitRunner.class)
@DropwizardTestConfig(applicationClass = TestAuthApplication.class, yamlFile = "/test-default-authenticated.yaml")
public class DefaultServerWithAuthenticationSeleniumTest extends SeleniumTest {

    @Override
    protected String getSwaggerUrl() {
        return getSwaggerUrl(55555, Constants.SWAGGER_PATH);
    }

    @Test
    public void testProtectedResourceWithoutAuthorizationShouldReturn401() throws Exception {
        driver.get(getSwaggerUrl() + "#!/auth/protectedDummyEndpoint");
        driver.manage().timeouts().implicitlyWait(WAIT_IN_SECONDS, TimeUnit.SECONDS);

        clickOnTryOut("auth_protectedDummyEndpoint_content");
        assertResponseCodeIs("auth_protectedDummyEndpoint_content", 401);
    }

    @Test
    public void testProtectedResourceWithAuthorizationShouldReturn200() throws Exception {
        driver.get(getSwaggerUrl() + "#!/auth/protectedDummyEndpoint");
        driver.manage().timeouts().implicitlyWait(WAIT_IN_SECONDS, TimeUnit.SECONDS);

        String token = "secret";
        
        new Select(driver.findElement(By.id("input_headerSelect"))).selectByVisibleText("Auth Header");
        driver.findElement(By.id("input_authHeader")).sendKeys("Bearer " + token);

        clickOnTryOut("auth_protectedDummyEndpoint_content");
        assertResponseCodeIs("auth_protectedDummyEndpoint_content", 200);
        assertResponseBodyIs("auth_protectedDummyEndpoint_content", token);
    }


}
