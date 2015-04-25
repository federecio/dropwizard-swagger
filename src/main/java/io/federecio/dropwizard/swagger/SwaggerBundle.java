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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.ImmutableMap;
import com.wordnik.swagger.jaxrs.config.BeanConfig;
import com.wordnik.swagger.jaxrs.listing.ApiListingResource;
import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

/**
 * @author Federico Recio
 * @author Flemming Frandsen
 * @author Tristan Burch
 */
public class SwaggerBundle<T extends Configuration> implements ConfiguredBundle<T> {

    @Override
    public void initialize(Bootstrap<?> bootstrap) {
        bootstrap.addBundle(new ViewBundle<Configuration>() {
            @Override
            public ImmutableMap<String, ImmutableMap<String, String>> getViewConfiguration(final Configuration configuration) {
                return ImmutableMap.of();
            }
        });
    }

    @Override
    public void run(T configuration, Environment environment) throws Exception {
        String providedUriPrefix = getUriPrefix();
        if (providedUriPrefix == null) {
            providedUriPrefix = getUriPrefix(configuration);
        }

        SwaggerConfiguration swaggerConfiguration = new SwaggerConfiguration(configuration);

        final String rootPath = providedUriPrefix != null ? providedUriPrefix : swaggerConfiguration.getJerseyRootPath();
        final String urlPattern = providedUriPrefix != null ? providedUriPrefix : swaggerConfiguration.getUrlPattern();

        String uriPathPrefix = rootPath.equals("/") ? "" : rootPath;
        new AssetsBundle(Constants.SWAGGER_RESOURCES_PATH, uriPathPrefix + Constants.SWAGGER_URI_PATH, null, Constants.SWAGGER_ASSETS_NAME).run(environment);

        environment.jersey().register(new SwaggerResource(urlPattern));

        environment.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

        BeanConfig config = new BeanConfig();
        config.setTitle("Swagger sample app");
        config.setVersion("1.0.0");

        config.setBasePath(urlPattern);
        config.setResourcePackage("io.federecio.dropwizard.swagger");
        config.setScan(true);

        environment.jersey().register(new ApiListingResource());
    }

    @SuppressWarnings("unused")
    protected String getUriPrefix() {
        return null;
    }

    @SuppressWarnings("unused")
    protected String getUriPrefix(T configuration) {
        return null;
    }
}
