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
import io.dropwizard.server.DefaultServerFactory;
import io.dropwizard.server.ServerFactory;
import io.dropwizard.server.SimpleServerFactory;

/**
 * @author Federico Recio
 * @author Flemming Frandsen
 */
public class SwaggerConfiguration {

    private final Configuration configuration;

    public SwaggerConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public String getJerseyRootPath() {
        String rootPath;

        ServerFactory serverFactory = configuration.getServerFactory();

        if (serverFactory instanceof SimpleServerFactory) {
            rootPath = ((SimpleServerFactory) serverFactory).getJerseyRootPath();
        } else {
            rootPath = ((DefaultServerFactory) serverFactory).getJerseyRootPath();
        }

        return stripUrlSlashes(rootPath);
    }

    public String getUrlPattern() {
        final String applicationContextPath = getApplicationContextPath();
        final String rootPath = getJerseyRootPath();

        String urlPattern;

        if (rootPath.equals("/") && applicationContextPath.equals("/")) {
            urlPattern = "/";
        } else if (rootPath.equals("/") && !applicationContextPath.equals("/")) {
            urlPattern = applicationContextPath;
        } else if (!rootPath.equals("/") && applicationContextPath.equals("/")) {
            urlPattern = rootPath;
        } else {
            urlPattern = applicationContextPath + rootPath;
        }

        return urlPattern;
    }

    private String getApplicationContextPath() {
        String applicationContextPath;

        ServerFactory serverFactory = configuration.getServerFactory();

        if (serverFactory instanceof SimpleServerFactory) {
            applicationContextPath = ((SimpleServerFactory) serverFactory).getApplicationContextPath();
        } else {
            applicationContextPath = ((DefaultServerFactory) serverFactory).getApplicationContextPath();
        }

        return stripUrlSlashes(applicationContextPath);
    }

    private String stripUrlSlashes(String urlToStrip) {
        if (urlToStrip.endsWith("/*")) {
            urlToStrip = urlToStrip.substring(0, urlToStrip.length() - 1);
        }

        if (urlToStrip.length() > 1 && urlToStrip.endsWith("/")) {
            urlToStrip = urlToStrip.substring(0, urlToStrip.length() - 1);
        }

        return urlToStrip;
    }
}
