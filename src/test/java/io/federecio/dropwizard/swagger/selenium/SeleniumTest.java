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
