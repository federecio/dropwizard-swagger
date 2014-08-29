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
package io.federecio.dropwizard.swagger;

import com.jayway.restassured.RestAssured;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Federico Recio
 */
public abstract class DropwizardTest {

    private final int port;
    private final String path;

    protected DropwizardTest(int port, String path) {
        this.port = port;
        this.path = path;
    }

    @Before
    public void setPort() {
        RestAssured.port = port;
    }

    @Test
    public void resourceIsAvailable() throws Exception {
        if (path.equals("/")) {
            RestAssured.expect().statusCode(HttpStatus.OK_200).when().get("/test.json");
        } else {
            RestAssured.expect().statusCode(HttpStatus.OK_200).when().get(path + "/test.json");
        }
    }

    @Test
    public void swaggerIsAvailable() throws Exception {
        if (path.equals("/")) {
            RestAssured.expect().statusCode(HttpStatus.OK_200).when().get("/api-docs");
            RestAssured.expect().statusCode(HttpStatus.OK_200).when().get("/api-docs/test");
            RestAssured.expect().statusCode(HttpStatus.OK_200).when().get("/swagger");
            RestAssured.expect().statusCode(HttpStatus.OK_200).when().get("/swagger/");
        } else {
            RestAssured.expect().statusCode(HttpStatus.OK_200).when().get(path + "/api-docs");
            RestAssured.expect().statusCode(HttpStatus.OK_200).when().get(path + "/api-docs/test");
            RestAssured.expect().statusCode(HttpStatus.OK_200).when().get(path + "/swagger");
            RestAssured.expect().statusCode(HttpStatus.OK_200).when().get(path + "/swagger/");
        }
    }
}
