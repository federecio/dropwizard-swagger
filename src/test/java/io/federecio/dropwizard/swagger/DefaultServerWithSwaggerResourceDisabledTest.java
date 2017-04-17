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
package io.federecio.dropwizard.swagger;

import org.eclipse.jetty.http.HttpStatus;
import org.hamcrest.core.StringContains;
import org.junit.ClassRule;
import org.junit.Test;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import io.restassured.RestAssured;

public class DefaultServerWithSwaggerResourceDisabledTest
        extends DropwizardCommonTest {

    @ClassRule
    public static final DropwizardAppRule<TestConfiguration> RULE = new DropwizardAppRule<TestConfiguration>(
            TestApplication.class, ResourceHelpers.resourceFilePath(
                    "test-default-without-swagger-resource.yaml"));

    public DefaultServerWithSwaggerResourceDisabledTest() {
        super(RULE.getLocalPort(), "/");
    }

    @Test
    public void swaggerIsAvailable() throws Exception {
        RestAssured.expect().statusCode(HttpStatus.OK_200)
                .body(StringContains
                        .containsString(TestResource.OPERATION_DESCRIPTION))
                .when().get(Path.from(basePath, "swagger.json"));
        RestAssured.expect().statusCode(HttpStatus.NOT_FOUND_404).when()
                .get(Path.from(basePath, "swagger"));
        RestAssured.expect().statusCode(HttpStatus.NOT_FOUND_404).when()
                .get(Path.from(basePath, "swagger") + "/");
    }
}
