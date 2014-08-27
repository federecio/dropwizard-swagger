package io.federecio.dropwizard.swagger.selenium;

import io.federecio.dropwizard.junitrunner.DropwizardJunitRunner;
import io.federecio.dropwizard.junitrunner.DropwizardTestConfig;
import io.federecio.dropwizard.swagger.TestApplication;
import org.junit.runner.RunWith;

/**
 * @author Federico Recio
 */
@RunWith(DropwizardJunitRunner.class)
@DropwizardTestConfig(applicationClass = TestApplication.class, yamlFile = "/test-default.yaml")
public class DefaultServerSeleniumTest extends SeleniumTest {

    @Override
    protected String getSwaggerUrl() {
        return "http://localhost:44444/swagger";
    }
}
