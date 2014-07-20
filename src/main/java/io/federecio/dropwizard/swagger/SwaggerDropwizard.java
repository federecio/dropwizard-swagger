/**
 * Copyright (C) 2014 Federico Recio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.federecio.dropwizard.swagger;

import io.dropwizard.Configuration;
import io.dropwizard.server.ServerFactory;
import io.dropwizard.server.SimpleServerFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

import java.io.IOException;

/**
 * @author Federico Recio
 */
public class SwaggerDropwizard {


    public void onInitialize(Bootstrap<?> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle());
        bootstrap.addBundle(new ViewBundle());
    }

    public void onRun(Configuration configuration, Environment environment) throws IOException {
        _onRun(configuration, environment);
        SwaggerBundle.configure(configuration);
    }

    /**
     * Call this method instead of {@link this#onRun(Configuration, Environment)} if the automatic host detection
     * does not work correctly.
     */
    public void onRun(Configuration configuration, Environment environment, String host) {
        _onRun(configuration, environment);
        SwaggerBundle.configure(configuration, host);
    }

    private void _onRun(Configuration configuration, Environment environment) {
        String applicationContextPath;
        ServerFactory serverFactory = configuration.getServerFactory();
        if (serverFactory instanceof SimpleServerFactory) {
            applicationContextPath = ((SimpleServerFactory) serverFactory).getApplicationContextPath();
        } else {
            applicationContextPath = "/";
        }
        environment.jersey().register(new SwaggerResource(applicationContextPath));
    }
}
