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

import com.google.common.base.Optional;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicAuthFactory;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class TestApplicationWithAuth extends Application<TestConfiguration> {

    @Override
    public void initialize(Bootstrap<TestConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<TestConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(TestConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
    }

    @Override
    public void run(TestConfiguration configuration, Environment environment) throws Exception {
        environment.jersey().register(AuthFactory.binder(new BasicAuthFactory<>(new TestAuthenticator(),
                "Auth", String.class)));
        environment.jersey().register(new TestResourceWithAuth());
    }

    private static class TestAuthenticator implements Authenticator<BasicCredentials, String> {

        @Override
        public Optional<String> authenticate(BasicCredentials basicCredentials) throws AuthenticationException {
            return Optional.fromNullable(basicCredentials.getUsername());
        }
    }
}
