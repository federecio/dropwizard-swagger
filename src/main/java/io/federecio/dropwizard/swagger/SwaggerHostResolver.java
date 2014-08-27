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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * @author Federico Recio
 */
public class SwaggerHostResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(SwaggerHostResolver.class);
    private static final String DEFAULT_SWAGGER_HOST = "localhost";
    private static final String AWS_HOST_NAME_URL = "http://169.254.169.254/latest/meta-data/public-hostname/";

    /**
     * Attempts to determine the host to be used in order to configure Swagger, as the time of writing
     * this comment Swagger expects a full URL to be passed in its {@link com.wordnik.swagger.config.SwaggerConfig}
     * <p/>
     * The strategy is:
     * <p/>
     * 1 - IF the folder /var/lib/cloud is present THEN set host name to the result of GET http://169.254.169.254/latest/meta-data/public-hostname/
     * 2 - InetAddress.getLocalHost().getHostName()
     * 3 - localhost
     */
    public static String getSwaggerHost() throws IOException {
        String host;
        if (new File("/var/lib/cloud/").exists()) {
            LOGGER.info("Folder /var/lib/cloud exists so assuming we are running on AWS, will attempt to get host name from " + AWS_HOST_NAME_URL);
            HttpURLConnection urlConnection = (HttpURLConnection) new URL(AWS_HOST_NAME_URL).openConnection();
            urlConnection.setRequestMethod("GET");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
                host = reader.readLine();
            }
        } else {
            try {
                host = InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException e) {
                LOGGER.warn("Unable to determine host for swagger, using default value");
                host = DEFAULT_SWAGGER_HOST;
            }
        }
        LOGGER.info("Setting host for swagger to {}", host);
        return host;
    }
}
