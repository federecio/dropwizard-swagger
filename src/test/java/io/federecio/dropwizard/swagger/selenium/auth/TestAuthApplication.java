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
import io.swagger.config.FilterFactory;
import io.swagger.core.filter.SwaggerSpecFilter;
import io.swagger.model.ApiDescription;
import io.swagger.models.Model;
import io.swagger.models.Operation;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.properties.Property;

import java.util.List;
import java.util.Map;

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

        FilterFactory.setFilter(new SwaggerSpecFilter() {

            @Override
            public boolean isOperationAllowed(Operation operation,
                                              ApiDescription apiDescription,
                                              Map<String, List<String>> map,
                                              Map<String, String> map1,
                                              Map<String, List<String>> map2) {
                return true;
            }

            @Override
            public boolean isParamAllowed(Parameter parameter,
                                          Operation operation,
                                          ApiDescription apiDescription,
                                          Map<String, List<String>> map,
                                          Map<String, String> map1,
                                          Map<String, List<String>> map2) {
                return !parameterAccessValueIsHidden(parameter);
            }

            @Override
            public boolean isPropertyAllowed(Model model,
                                             Property property,
                                             String s, Map<String, List<String>> map, Map<String, String> map1, Map<String, List<String>> map2) {
                return true;
            }

            public boolean parameterAccessValueIsHidden(Parameter parameter) {
                return parameter.getAccess() != null && parameter.getAccess().equalsIgnoreCase("hidden");
            }
        });
    }
}
