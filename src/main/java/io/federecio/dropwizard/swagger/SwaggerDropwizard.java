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

import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

import java.io.IOException;

import com.google.common.collect.ImmutableMap;
import com.wordnik.swagger.config.ScannerFactory;
import com.wordnik.swagger.jaxrs.config.DefaultJaxrsScanner;
import com.wordnik.swagger.jaxrs.listing.ApiDeclarationProvider;
import com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON;
import com.wordnik.swagger.jaxrs.listing.ResourceListingProvider;
import com.wordnik.swagger.jaxrs.reader.DefaultJaxrsApiReader;
import com.wordnik.swagger.reader.ClassReaders;

/**
 * @author Federico Recio
 * @author Flemming Frandsen
 */
public class SwaggerDropwizard<T extends Configuration> implements ConfiguredBundle<T> {

    @Override
    public void initialize(Bootstrap<?> bootstrap) {
        onInitialize(bootstrap);
    }

    public void onInitialize(Bootstrap<?> bootstrap) {
        bootstrap.addBundle(new ViewBundle<Configuration>() {
            @Override
            public ImmutableMap<String, ImmutableMap<String, String>> getViewConfiguration(final Configuration configuration) {
                return ImmutableMap.of();
            }
        });
    }

    @Override
    public void run(T configuration, Environment environment) throws Exception {
        onRun(configuration, environment);
    }

    public void onRun(T configuration, Environment environment) throws IOException {
        String host = SwaggerHostResolver.getSwaggerHost();
        onRun(configuration, environment, host);
    }

    /**
     * Call this method instead of {@link this#onRun(Configuration, Environment)} if the automatic host detection
     * does not work correctly.
     */
    public void onRun(T configuration, Environment environment, String host) {
        onRun(configuration, environment, host, null);
    }

    /**
     * Call this method instead of {@link this#onRun(Configuration, Environment)} if the automatic host detection
     * does not work correctly.
     */
    public void onRun(T configuration, Environment environment, String host, Integer port) {
        SwaggerConfiguration swaggerConfiguration = new SwaggerConfiguration(configuration, environment);

        final String rootPath = swaggerConfiguration.getJerseyRootPath();
        final String urlPattern = swaggerConfiguration.getUrlPattern();

        if (rootPath.equals("/")) {
            new AssetsBundle(Constants.SWAGGER_RESOURCES_PATH, Constants.SWAGGER_URI_PATH, null, Constants.SWAGGER_ASSETS_NAME).run(environment);
        } else {
            new AssetsBundle(Constants.SWAGGER_RESOURCES_PATH, rootPath + Constants.SWAGGER_URI_PATH, null, Constants.SWAGGER_ASSETS_NAME).run(environment);
        }

        environment.jersey().register(new SwaggerResource(urlPattern));

        swaggerConfiguration.setUpSwaggerFor(host, port);

        environment.jersey().register(new ApiListingResourceJSON());
        environment.jersey().register(new ApiDeclarationProvider());
        environment.jersey().register(new ResourceListingProvider());
        ScannerFactory.setScanner(new DefaultJaxrsScanner());
        ClassReaders.setReader(new DefaultJaxrsApiReader());
    }
}
