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
@DropwizardTestConfig(applicationClass = TestService.class, yamlFile = "/test-simple-with-path.yaml")
public class SimpleServerWithPathTest {

    @BeforeClass
    public static void setPort() {
        RestAssured.port = 55667;
    }

    @Test
    public void resourceIsAvailable() throws Exception {
        RestAssured.expect().statusCode(HttpStatus.OK_200).when().get("/api/test.json");
    }

    @Test
    public void swaggerIsAvailable() throws Exception {
        RestAssured.expect().statusCode(HttpStatus.OK_200).when().get("/api/api-docs");
        RestAssured.expect().statusCode(HttpStatus.OK_200).when().get("/api/api-docs/test");
        RestAssured.expect().statusCode(HttpStatus.OK_200).when().get("/api/swagger");
    }
}
