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

import com.wordnik.swagger.config.ConfigFactory;
import com.wordnik.swagger.config.SwaggerConfig;
import io.dropwizard.Configuration;
import io.dropwizard.jetty.ConnectorFactory;
import io.dropwizard.jetty.HttpConnectorFactory;
import io.dropwizard.jetty.HttpsConnectorFactory;
import io.dropwizard.server.DefaultServerFactory;
import io.dropwizard.server.ServerFactory;
import io.dropwizard.server.SimpleServerFactory;
import io.dropwizard.setup.Environment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Federico Recio
 */
public class SwaggerConfiguration {

    private final Configuration configuration;
    private final Environment environment;

    public SwaggerConfiguration(Configuration configuration, Environment environment) {
        this.configuration = configuration;
        this.environment = environment;
    }

    public void setUpSwaggerFor(String host) {
        SwaggerConfig config = ConfigFactory.config();
        String swaggerBasePath = getSwaggerBasePath(host);
        config.setBasePath(swaggerBasePath);
        config.setApiPath(swaggerBasePath);
        ConfigFactory.setConfig(config);
    }

    public String getContextPath() {
        String applicationContextPath;
        ServerFactory serverFactory = configuration.getServerFactory();
        if (serverFactory instanceof SimpleServerFactory) {
            applicationContextPath = ((SimpleServerFactory) serverFactory).getApplicationContextPath();
        } else {
            String urlPattern = environment.jersey().getUrlPattern();
            if (urlPattern.endsWith("/*")) {
                urlPattern = urlPattern.substring(0, urlPattern.length() - 1);
            }
            if (urlPattern.length() > 1 && urlPattern.endsWith("/")) {
                urlPattern = urlPattern.substring(0, urlPattern.length() - 1);
            }
            applicationContextPath = urlPattern;
        }
        return applicationContextPath;
    }

    public boolean isSimpleServer() {
        return configuration.getServerFactory() instanceof SimpleServerFactory;
    }

    private String getSwaggerBasePath(String host) {
        HttpConnectorFactory httpConnectorFactory = getHttpConnectionFactory();

        if (httpConnectorFactory == null) {
            throw new IllegalStateException("Could not get HttpConnectorFactory");
        }

        String protocol = httpConnectorFactory instanceof HttpsConnectorFactory ? "https" : "http";
        String contextPath = getContextPath();
        if (!"/".equals(contextPath)) {
            return String.format("%s://%s:%s%s", protocol, host, httpConnectorFactory.getPort(), contextPath);
        } else {
            return String.format("%s://%s:%s", protocol, host, httpConnectorFactory.getPort());
        }
    }

    private HttpConnectorFactory getHttpConnectionFactory() {
        List<ConnectorFactory> connectorFactories = getConnectorFactories();
        for (ConnectorFactory connectorFactory : connectorFactories) {
            if (connectorFactory instanceof HttpsConnectorFactory) {
                return (HttpConnectorFactory) connectorFactory;  // if we find https skip the others
            }
        }
        for (ConnectorFactory connectorFactory : connectorFactories) {
            if (connectorFactory instanceof HttpConnectorFactory) {
                return (HttpConnectorFactory) connectorFactory; // if not https pick http
            }
        }

        throw new IllegalStateException("Unable to find an HttpServerFactory");
    }

    private List<ConnectorFactory> getConnectorFactories() {
        ServerFactory serverFactory = configuration.getServerFactory();
        List<ConnectorFactory> connectorFactories = new ArrayList<>();
        if (serverFactory instanceof SimpleServerFactory) {
            connectorFactories.add(((SimpleServerFactory) serverFactory).getConnector());
        } else if (serverFactory instanceof DefaultServerFactory) {
            connectorFactories.addAll(((DefaultServerFactory) serverFactory).getApplicationConnectors());
        } else {
            throw new IllegalStateException("Unknown ServerFactory implementation: " + serverFactory.getClass());
        }
        return connectorFactories;
    }
}
