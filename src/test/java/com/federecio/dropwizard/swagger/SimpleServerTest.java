package com.federecio.dropwizard.swagger;

import org.eclipse.jetty.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.federecio.dropwizard.junitrunner.DropwizardJunitRunner;
import com.federecio.dropwizard.junitrunner.DropwizardTestConfig;
import com.jayway.restassured.RestAssured;

/**
 * @author Federico Recio
 */
@RunWith(DropwizardJunitRunner.class)
@DropwizardTestConfig(applicationClass = TestService.class, yamlFile = "/test-simple-root-path.yaml")
public class SimpleServerTest {

    @BeforeClass
    public static void setPort() {
        RestAssured.port = 55668;
    }

    @Test
    public void resourceIsAvailable() throws Exception {
        RestAssured.expect().statusCode(HttpStatus.OK_200).when().get("/test.json");
    }

    @Test
    public void swaggerIsAvailable() throws Exception {
        RestAssured.expect().statusCode(HttpStatus.OK_200).when().get("/api-docs");
        RestAssured.expect().statusCode(HttpStatus.OK_200).when().get("/api-docs/test");
        RestAssured.expect().statusCode(HttpStatus.OK_200).when().get("/swagger");
    }
}
