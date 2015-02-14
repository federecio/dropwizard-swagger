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
import io.dropwizard.setup.Environment;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

/**
 * @author Tristan Burch
 */
public class SwaggerBundle<T extends Configuration> extends SwaggerDropwizard<T> {

    public SwaggerBundleConfiguration getSwaggerBundleConfiguration(T configuration) {
        try {
            return new SwaggerBundleConfiguration(SwaggerHostResolver.getSwaggerHost());
        } catch (IOException e) {
            throw new RuntimeException("Couldn't determine host");
        }
    }

    @Override
    public void run(T configuration, Environment environment) {
        SwaggerBundleConfiguration bundleConfiguration = getSwaggerBundleConfiguration(configuration);
        try {
            if (bundleConfiguration == null) {
                onRun(configuration, environment);
            } else {
                String host = StringUtils.isEmpty(bundleConfiguration.getHost()) ? SwaggerHostResolver.getSwaggerHost() : bundleConfiguration.getHost();
                super.onRun(configuration, environment, host, bundleConfiguration.getPort());
            }
        } catch (IOException e) {
            throw new RuntimeException("Couldn't determine host");
        }
    }
}
