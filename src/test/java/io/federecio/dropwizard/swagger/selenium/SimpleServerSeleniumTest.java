package io.federecio.dropwizard.swagger.selenium;

import io.federecio.dropwizard.junitrunner.DropwizardJunitRunner;
import io.federecio.dropwizard.junitrunner.DropwizardTestConfig;
import io.federecio.dropwizard.swagger.TestApplication;
import org.junit.runner.RunWith;

@DropwizardTestConfig(applicationClass = TestApplication.class, yamlFile = "/test-simple-root-path.yaml")
@RunWith(DropwizardJunitRunner.class)
public class SimpleServerSeleniumTest extends SeleniumTest {

    @Override
    protected String getSwaggerUrl() {
        return "http://localhost:55668/swagger";
    }
}
