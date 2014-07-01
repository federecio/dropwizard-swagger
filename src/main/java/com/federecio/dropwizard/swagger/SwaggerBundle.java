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
package com.federecio.dropwizard.swagger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wordnik.swagger.config.ConfigFactory;
import com.wordnik.swagger.config.ScannerFactory;
import com.wordnik.swagger.config.SwaggerConfig;
import com.wordnik.swagger.jaxrs.config.DefaultJaxrsScanner;
import com.wordnik.swagger.jaxrs.listing.ApiDeclarationProvider;
import com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON;
import com.wordnik.swagger.jaxrs.listing.ResourceListingProvider;
import com.wordnik.swagger.jaxrs.reader.DefaultJaxrsApiReader;
import com.wordnik.swagger.reader.ClassReaders;
import io.dropwizard.Configuration;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.jetty.ConnectorFactory;
import io.dropwizard.jetty.HttpConnectorFactory;
import io.dropwizard.jetty.HttpsConnectorFactory;
import io.dropwizard.server.DefaultServerFactory;
import io.dropwizard.server.ServerFactory;
import io.dropwizard.server.SimpleServerFactory;
import io.dropwizard.setup.Environment;

/**
 * @author Federico Recio
 */
public class SwaggerBundle extends AssetsBundle {

    public static final String PATH = "/swagger-static";
    private static final Logger LOGGER = LoggerFactory.getLogger(SwaggerBundle.class);

    public SwaggerBundle() {
        super(PATH);
    }

    /**
     * Call this method from {@link io.dropwizard.Application#run(io.dropwizard.Configuration, io.dropwizard.setup.Environment)}
     * passing the {@link io.dropwizard.Configuration} so that Swagger can be properly configured to run on Amazon.
     * <p/>
     * It is recommended that this method is called even when running locally -which has no side effect- so that this application
     * can be deployed to an EC2 instance without any changes.
     */
    public static void configure(Configuration configuration) throws IOException {
        String host = determineHost();
        configure(configuration, host);
    }

    /**
     * Call this method directly if not running locally or in AWS and pass in the host to which Swagger API
     * should be bound to.
     */
    public static void configure(Configuration configuration, String host) {
        SwaggerConfig config = ConfigFactory.config();
        String swaggerBasePath = getSwaggerBasePath(configuration, host);
        config.setBasePath(swaggerBasePath);
        config.setApiPath(swaggerBasePath);
    }

    private static String getSwaggerBasePath(Configuration configuration, String host) {
        String applicationContextPath = "/";
        ServerFactory serverFactory = configuration.getServerFactory();
        HttpConnectorFactory httpConnectorFactory = null;

        if (serverFactory instanceof SimpleServerFactory) {
            applicationContextPath = ((SimpleServerFactory) serverFactory).getApplicationContextPath();
            ConnectorFactory cf = ((SimpleServerFactory) serverFactory).getConnector();
            if (cf instanceof HttpsConnectorFactory) {
                httpConnectorFactory = (HttpConnectorFactory) cf;
            } else if (cf instanceof HttpConnectorFactory) {
                httpConnectorFactory = (HttpConnectorFactory) cf;
            }
        } else if (serverFactory instanceof DefaultServerFactory) {
            List<ConnectorFactory> applicationConnectors = ((DefaultServerFactory) serverFactory).getApplicationConnectors();
            for (ConnectorFactory connectorFactory : applicationConnectors) {
                if (connectorFactory instanceof HttpsConnectorFactory) {
                    httpConnectorFactory = (HttpConnectorFactory) connectorFactory;
                }
            }
            if (httpConnectorFactory == null) { // not https
                for (ConnectorFactory connectorFactory : applicationConnectors) {
                    if (connectorFactory instanceof HttpConnectorFactory) {
                        httpConnectorFactory = (HttpConnectorFactory) connectorFactory;
                    }
                }
            }
        }

        if (httpConnectorFactory == null) {
            throw new IllegalStateException("Could not get HttpConnectorFactory");
        }

        String protocol = httpConnectorFactory instanceof HttpsConnectorFactory ? "https" : "http";

        if (!"/".equals(applicationContextPath)) {
            return String.format("%s://%s:%s%s", protocol, host, httpConnectorFactory.getPort(), applicationContextPath);
        } else {
            return String.format("%s://%s:%s", protocol, host, httpConnectorFactory.getPort());
        }
    }

    private static String determineHost() throws IOException {
        String host;

        if (!new File("/var/lib/cloud/").exists()) {
            LOGGER.info("/var/lib/cloud does not exist, assuming that we are running locally");
            host = "localhost";
        } else {
            HttpURLConnection urlConnection = (HttpURLConnection) new URL("http://169.254.169.254/latest/meta-data/public-hostname/").openConnection();
            urlConnection.setRequestMethod("GET");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
                host = reader.readLine();
            }
        }
        return host;
    }

    @Override
    public void run(Environment environment) {
        environment.jersey().register(new ApiListingResourceJSON());
        environment.jersey().register(new ApiDeclarationProvider());
        environment.jersey().register(new ResourceListingProvider());
        ScannerFactory.setScanner(new DefaultJaxrsScanner());
        ClassReaders.setReader(new DefaultJaxrsApiReader());
        super.run(environment);
    }
}
