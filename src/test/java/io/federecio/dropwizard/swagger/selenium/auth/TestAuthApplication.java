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

import com.google.common.base.Optional;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicAuthFactory;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.auth.oauth.OAuthFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import io.federecio.dropwizard.swagger.TestApplication;
import io.federecio.dropwizard.swagger.TestConfiguration;
import io.federecio.dropwizard.swagger.TestResource;

/**
 * @author Maximilien Marie
 */
public class TestAuthApplication extends TestApplication {

    @Override
    public void run(TestConfiguration configuration, Environment environment) throws Exception {
        super.run(configuration, environment);
        environment.jersey().register(new AuthResource());

        environment.jersey().register(AuthFactory.binder(new OAuthFactory<>(new Authenticator<String, String>() {
            @Override
            public Optional<String> authenticate(String token) throws AuthenticationException {
                if ("secret".equals(token)) {
                    return Optional.of(token);
                }
                return Optional.absent();
            }
        },
                "SUPER SECRET STUFF",
                String.class)));
    }
}
